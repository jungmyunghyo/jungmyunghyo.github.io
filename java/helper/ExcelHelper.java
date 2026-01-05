import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.Comment;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Hyperlink;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class ExcelHelper {
	private static final class Constant {
		private static final int FILE_SIZE_MIN = 4;
		private static final int FILE_SIZE_MAX = 100 * 1024 * 1024;
		
		private static final String FILE_CONTENT_TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
		private static final String FILE_NAME_DEFAULT = "목록";
		private static final String FILE_NAME_PREFIX = "/";
		private static final String FILE_DATE_PREFIX = "_";
		private static final DateTimeFormatter FILE_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
		private static final String FILE_EXTENSION = ".xlsx";
		
		private static final int EXCEL_CHUNK_SIZE = 1_000;
		private static final int EXCEL_FLUSH_INTERVAL = EXCEL_CHUNK_SIZE / 5;
		private static final int STREAM_BUFFER_SIZE = 256 * 1024;
		
		private static final DateTimeFormatter QUERY_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		
		private static final int CELL_WIDTH_DEFAULT = 5_500;
		private static final String CELL_VALUE_DEFAULT = "";
		
		private static final Row.MissingCellPolicy MISSING_CELL_POLICY = Row.MissingCellPolicy.RETURN_BLANK_AS_NULL;
		
		private static final String ROW_NUMBER_KEY = "_rowNumber";
		private static final String ROW_NUMBER_HEADER = "열";
		private static final int ROW_NUMBER_WIDTH = 2_500;
		
		private static final String SHEET_TITLE_IS_SUCCESS = "성공_";
		private static final String SHEET_TITLE_IS_FAIL = "실패_";
		private static final String SHEET_TITLE_OF_COUNT = "_건";
		private static final String FILE_NAME_IS_SUCCESS_FAIL = "성공_및_실패";
		
		private static final String ERROR_COMMENT_AUTHOR = "System";
	}
	public static final class Vo {
		@Getter
		@Setter
		@NoArgsConstructor(access=AccessLevel.PUBLIC)
		@AllArgsConstructor(access=AccessLevel.PRIVATE)
		public static final class Write {
			private List<Ssheet> sheetList;
			private String fileName = Constant.FILE_NAME_DEFAULT;
			private Boolean cellStyleRequired;
			
			private String getFullFileName() {return this.fileName.replaceAll("[\\\\/:*?\"<>|]", "").trim() + Constant.FILE_DATE_PREFIX + LocalDateTime.now().format(Constant.FILE_DATE_TIME_FORMATTER) + Constant.FILE_EXTENSION;}
			private boolean getSafeCellStyleRequired() {return this.cellStyleRequired != null ? this.cellStyleRequired : false;}
			
			@Getter
			@Setter
			@NoArgsConstructor(access=AccessLevel.PUBLIC)
			@AllArgsConstructor(access=AccessLevel.PRIVATE)
			public static final class Ssheet {
				private String sheetTitle;
				private List<Map<String, String>> dataList;
				private List<String> dataKeyList;
				private List<String> headerList;
				private List<Integer> cellWidthList;
				private List<FormatHelper.Type.Format> cellTypeList;
				private String queryEndDateTime;
				
				private List<String> getSafeHeaderList() {return this.headerList != null && this.headerList.size() == this.dataKeyList.size() ? this.headerList : this.dataKeyList;}
				private List<Integer> getSafeCellWidthList() {return this.cellWidthList != null && this.cellWidthList.size() == this.dataKeyList.size() ? this.cellWidthList : new ArrayList<>(Collections.nCopies(dataKeyList.size(), Constant.CELL_WIDTH_DEFAULT));}
				private List<FormatHelper.Type.Format> getSafeCellTypeList() {return this.cellTypeList != null && this.cellTypeList.size() == this.dataKeyList.size() ? this.cellTypeList : new ArrayList<>(Collections.nCopies(dataKeyList.size(), FormatHelper.Type.Format.STRING));}
				public void setDataListWithTime(List<Map<String, String>> dataList) {
					this.dataList = dataList;
					this.queryEndDateTime = LocalDateTime.now().format(Constant.QUERY_DATE_TIME_FORMATTER);
				}
			}
		}
		@Getter
		@Setter
		@NoArgsConstructor(access=AccessLevel.PUBLIC)
		@AllArgsConstructor(access=AccessLevel.PRIVATE)
		public static final class Read {
			private List<String> dataKeyList;
			private List<Boolean> dataRequiredList;
			private List<Map<String, String>> successList;
			private List<Map<String, String>> failList;
			
			private List<Boolean> getSafeDataRequiredList() {return this.dataRequiredList != null && this.dataRequiredList.size() == this.dataKeyList.size() ? this.dataRequiredList : new ArrayList<>(Collections.nCopies(dataKeyList.size(), true));}
			public int getSuccessCount() {return this.successList == null || this.successList.isEmpty() ? 0 : this.successList.size();}
			public int getFailCount() {return this.failList == null || this.failList.isEmpty() ? 0 : this.failList.size();}
		}
	}
	public static final class Util {
		public static final String create(String filePath, Vo.Write write) throws IOException {
			try (SXSSFWorkbook poiWorkbook = new SXSSFWorkbook(Constant.EXCEL_CHUNK_SIZE)) {
				writeWorkbook(poiWorkbook, write);
				
				String fileFullPath = filePath + Constant.FILE_NAME_PREFIX + write.getFullFileName();
				try (FileOutputStream fos = new FileOutputStream(fileFullPath);
						BufferedOutputStream out = new BufferedOutputStream(fos, Constant.STREAM_BUFFER_SIZE)) {
					poiWorkbook.write(out);
					out.flush();
					fos.flush();
					return fileFullPath;
				} finally {
					poiWorkbook.dispose();
				}
			}
		}
		public static final File createAsFile(String filePath, Vo.Write write) throws IOException {
			return new File(create(filePath, write));
		}
		public static final MultipartFile createAsFile(Vo.Write write) throws IOException {
			try (SXSSFWorkbook poiWorkbook = new SXSSFWorkbook(Constant.EXCEL_CHUNK_SIZE)) {
				writeWorkbook(poiWorkbook, write);
				
				try (ByteArrayOutputStream out = new ByteArrayOutputStream(Constant.STREAM_BUFFER_SIZE)) {
					poiWorkbook.write(out);
					out.flush();
					
					final byte[] content = out.toByteArray();
					final String fileName = write.getFullFileName();
					final String contentType = Constant.FILE_CONTENT_TYPE;
					
					return new MultipartFile() {
						@Override
						public String getName() {return "file";}
						@Override
						public String getOriginalFilename() {return fileName;}
						@Override
						public String getContentType() {return contentType;}
						@Override
						public boolean isEmpty() {return content.length == 0;}
						@Override
						public long getSize() {return content.length;}
						@Override
						public byte[] getBytes() throws IOException {return content.clone();}
						@Override
						public InputStream getInputStream() throws IOException {return new ByteArrayInputStream(content);}
						@Override
						public void transferTo(File dest) throws IOException, IllegalStateException {try (FileOutputStream fos = new FileOutputStream(dest)) {fos.write(content);}}
					};
				} finally {
					poiWorkbook.dispose();
				}
			}
		}
		public static final void download(HttpServletResponse response, Vo.Write write) throws IOException {
			try (SXSSFWorkbook poiWorkbook = new SXSSFWorkbook(Constant.EXCEL_CHUNK_SIZE)) {
				writeWorkbook(poiWorkbook, write);
				
				response.setContentType(Constant.FILE_CONTENT_TYPE);
				response.setHeader("Content-disposition", "attachment;filename*=UTF-8''" + URLEncoder.encode(write.getFullFileName(), "UTF-8"));
				response.setHeader("Cache-Control", "no-cache; no-store; max-age=0; must-revalidate");
				response.setHeader("Set-Cookie", "fileDownload=true; path=/");
				
				try (BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream(), Constant.STREAM_BUFFER_SIZE)) {
					poiWorkbook.write(out);
					out.flush();
				} finally {
					poiWorkbook.dispose();
				}
			}
		}
		@SuppressWarnings("deprecation")
		private static final void writeWorkbook(SXSSFWorkbook poiWorkbook, Vo.Write write) throws IOException {
			poiWorkbook.setCompressTempFiles(true);
			
			CellStyle titleCellStyle = null;
			CellStyle dateCellStyle = null;
			CellStyle headerCellStyle = null;
			CellStyle bodyCellStyle = null;
			CellStyle linkBodyCellStyle = null;
			
			if (write.getSafeCellStyleRequired()) {
				titleCellStyle = Template.getTitleCellStyle(poiWorkbook);
				dateCellStyle = Template.getDateCellStyle(poiWorkbook);
				headerCellStyle = Template.getHeaderCellStyle(poiWorkbook);
				bodyCellStyle = Template.getBodyCellStyle(poiWorkbook);
				linkBodyCellStyle = Template.getLinkBodyCellStyle(poiWorkbook);
			}
			
			SXSSFSheet poiSheet;
			Row poiRow;
			Cell poiCell;
			CreationHelper poiHelper = poiWorkbook.getCreationHelper();
			
			List<Vo.Write.Ssheet> sheetList = write.getSheetList();
			int sheetSize = sheetList.size();
			
			for (int sheetNumber=0; sheetNumber<sheetSize; sheetNumber++) {
				Vo.Write.Ssheet sheet = sheetList.get(sheetNumber);
				
				String sheetTitle = sheet.getSheetTitle();
				String queryEndDateTime = sheet.getQueryEndDateTime();
				List<String> dataKeyList = sheet.getDataKeyList();
				List<String> headerList = sheet.getSafeHeaderList();
				List<Integer> cellWidthList = sheet.getSafeCellWidthList();
				List<FormatHelper.Type.Format> cellTypeList = sheet.getSafeCellTypeList();
				
				int cellSize = dataKeyList.size();
				int rowNumber = 0;
				
				poiSheet = poiWorkbook.createSheet(sheetTitle);
				for (int cellNumber=0; cellNumber<cellSize; cellNumber++) {
					poiSheet.setColumnWidth(cellNumber, cellWidthList.get(cellNumber));
				}
				
				poiRow = poiSheet.createRow(rowNumber++);
				poiCell = poiRow.createCell(0);
				poiCell.setCellType(CellType.STRING);
				poiCell.setCellValue(sheetTitle);
				poiCell.setCellStyle(titleCellStyle);
				poiSheet.addMergedRegion(Template.getCellRange(rowNumber, Math.min(cellSize, 7)));
				
				if (queryEndDateTime != null) {
					poiRow = poiSheet.createRow(rowNumber++);
					poiCell = poiRow.createCell(0);
					poiCell.setCellType(CellType.STRING);
					poiCell.setCellValue(queryEndDateTime);
					poiCell.setCellStyle(dateCellStyle);
					poiSheet.addMergedRegion(Template.getCellRange(rowNumber, Math.min(cellSize, 7)));
				}
				
				poiRow = poiSheet.createRow(rowNumber++);
				for (int cellNumber=0; cellNumber<cellSize; cellNumber++) {
					poiCell = poiRow.createCell(cellNumber);
					poiCell.setCellType(CellType.STRING);
					poiCell.setCellValue(headerList.get(cellNumber));
					poiCell.setCellStyle(headerCellStyle);
				}
				poiSheet.setAutoFilter(Template.getCellRange(rowNumber, cellSize));
				
				List<Map<String, String>> dataList = sheet.getDataList();
				if (dataList != null && !dataList.isEmpty()) {
					Drawing<?> poiCellDrawing = poiSheet.createDrawingPatriarch();
					
					int dataSize = dataList.size();
					
					for (int chunkNumber=0; chunkNumber<dataSize; chunkNumber+=Constant.EXCEL_CHUNK_SIZE) {
						final int chunkStartRow = chunkNumber;
						final int chunkEndRow = Math.min(chunkNumber + Constant.EXCEL_CHUNK_SIZE, dataSize);
						for (int dataNumber=chunkStartRow; dataNumber<chunkEndRow; dataNumber++) {
							Map<String, String> data = dataList.get(dataNumber);
							
							if (data != null && !data.isEmpty()) {
								poiRow = poiSheet.createRow(rowNumber++);
								for (int cellNumber=0; cellNumber<cellSize; cellNumber++) {
									Cell poiDataCell = poiRow.createCell(cellNumber);
									Object value = data.get(dataKeyList.get(cellNumber));
									String cellValue = value != null ? value.toString().trim() : "";
									try {
										FormatHelper.Type.Format format = cellTypeList.get(cellNumber);
										switch (format) {
											case INTEGER:
												poiDataCell.setCellType(CellType.NUMERIC);
												poiDataCell.setCellValue(FormatHelper.Util.parseLong(cellValue));
												poiDataCell.setCellStyle(bodyCellStyle);
												break;
											case DOUBLE:
												poiDataCell.setCellType(CellType.NUMERIC);
												poiDataCell.setCellValue(FormatHelper.Util.parseDouble(cellValue));
												poiDataCell.setCellStyle(bodyCellStyle);
												break;
											case URL_LINK:
												cellValue = FormatHelper.Util.parseUrlLink(cellValue.toLowerCase());
												
												poiDataCell.setCellType(CellType.STRING);
												poiDataCell.setCellValue(cellValue);
												poiDataCell.setCellStyle(linkBodyCellStyle);
												
												Hyperlink poiHyperlink = poiHelper.createHyperlink(HyperlinkType.URL);
												
												poiHyperlink.setAddress(cellValue);
												
												poiDataCell.setHyperlink(poiHyperlink);
												break;
											default:
												poiDataCell.setCellType(CellType.STRING);
												poiDataCell.setCellValue(FormatHelper.Util.parse(format, cellValue));
												poiDataCell.setCellStyle(bodyCellStyle);
												break;
										}
									} catch (Exception e) {
										poiDataCell.setCellType(CellType.STRING);
										poiDataCell.setCellValue(FormatHelper.Util.parseString(cellValue));
										poiDataCell.setCellStyle(bodyCellStyle);
										
										ClientAnchor poiCellAnchor = poiHelper.createClientAnchor();
										
										poiCellAnchor.setCol1(poiDataCell.getColumnIndex());
										poiCellAnchor.setRow1(poiDataCell.getRowIndex());
										poiCellAnchor.setCol2(poiCellAnchor.getCol1() + 2);
										poiCellAnchor.setRow2(poiCellAnchor.getRow1() + 2);
										
										Comment poiCellComment = poiCellDrawing.createCellComment(poiCellAnchor);
										
										poiCellComment.setString(poiHelper.createRichTextString(e.getClass().getSimpleName()));
										poiCellComment.setAuthor(Constant.ERROR_COMMENT_AUTHOR);
										
										poiDataCell.setCellComment(poiCellComment);
									}
								}
							}
							if (dataNumber > 0 && dataNumber % Constant.EXCEL_FLUSH_INTERVAL == 0) {
								poiSheet.flushRows();
							}
						}
					}
					dataList.clear();
				}
				poiSheet.flushRows();
			}
		}
		public static final Vo.Read readFromMultipart(MultipartFile multipartFile, Vo.Read read) throws IOException {
			if (multipartFile == null || multipartFile.isEmpty()) {
				throw new NullPointerException();
			}
			long fileSize = multipartFile.getSize();
			if (fileSize < Constant.FILE_SIZE_MIN) {
				throw new IllegalArgumentException();
			} else if (fileSize > Constant.FILE_SIZE_MAX) {
				throw new IllegalArgumentException();
			}
			if (!multipartFile.getOriginalFilename().toLowerCase().endsWith(Constant.FILE_EXTENSION)) {
				throw new IllegalArgumentException();
			}
			try (InputStream in = multipartFile.getInputStream()) {
				return readFromStream(in, read);
			}
		}
		public static final Vo.Read readFromBytes(byte[] fileBytes, Vo.Read read) throws IOException {
			if (fileBytes == null) {
				throw new NullPointerException();
			}
			long fileSize = fileBytes.length;
			if (fileSize < Constant.FILE_SIZE_MIN) {
				throw new IllegalArgumentException();
			} else if (fileSize > Constant.FILE_SIZE_MAX) {
				throw new IllegalArgumentException();
			}
			try (InputStream in = new ByteArrayInputStream(fileBytes)) {
				return readFromStream(in, read);
			}
		}
		public static final Vo.Read readFromStream(InputStream inputStream, Vo.Read read) throws IOException {
			if (inputStream == null) {
				throw new NullPointerException();
			}
			try (InputStream in = inputStream;
					Workbook poiWorkbook = WorkbookFactory.create(in)) {
				return readWorkbook(poiWorkbook, read);
			}
		}
		public static final Vo.Read readFromPath(String filePath, Vo.Read read) throws IOException {
			if (filePath == null || filePath.trim().isEmpty()) {
				throw new NullPointerException();
			}
			return readFromFile(new File(filePath), read);
		}
		public static final Vo.Read readFromFile(File file, Vo.Read read) throws IOException {
			if (file == null || !file.exists()) {
				throw new NullPointerException();
			}
			if (!file.isFile() || !file.canRead()) {
				throw new IllegalArgumentException();
			}
			long fileSize = file.length();
			if (fileSize < Constant.FILE_SIZE_MIN) {
				throw new IllegalArgumentException();
			} else if (fileSize > Constant.FILE_SIZE_MAX) {
				throw new IllegalArgumentException();
			}
			if (!file.getName().toLowerCase().endsWith(Constant.FILE_EXTENSION)) {
				throw new IllegalArgumentException();
			}
			try (Workbook poiWorkbook = WorkbookFactory.create(file)) {
				return readWorkbook(poiWorkbook, read);
			}
		}
		private static final Vo.Read readWorkbook(Workbook poiWorkbook, Vo.Read read) {
			if (poiWorkbook != null && poiWorkbook.getNumberOfSheets() > 0) {
				Sheet poiSheet = poiWorkbook.getSheetAt(0);
				if (poiSheet != null && poiSheet.getPhysicalNumberOfRows() > 0) {
					List<String> dataKeyList = read.getDataKeyList();
					List<Boolean> dataRequiredList = read.getSafeDataRequiredList();
					
					int cellSize = dataKeyList.size();
					int lastRowNumber = poiSheet.getLastRowNum();
					
					List<Map<String, String>> successList = new ArrayList<>(lastRowNumber + 1);
					List<Map<String, String>> failList = new ArrayList<>(lastRowNumber + 1);
					
					Row poiRow;
					Cell poiCell;
					List<CellRangeAddress> poiMergedRegions = poiSheet.getMergedRegions();
					int rowStartNumber = poiMergedRegions != null && !poiMergedRegions.isEmpty() ? 3 : 1;
					
					for (int rowNumber=rowStartNumber; rowNumber<=lastRowNumber; rowNumber++) {
						boolean isSuccess = true;
						Map<String, String> rowMap = new LinkedHashMap<>(cellSize + 1);
						
						poiRow = poiSheet.getRow(rowNumber);
						if (poiRow != null) {
							for (int cellNumber=0; cellNumber<cellSize; cellNumber++) {
								poiCell = poiRow.getCell(cellNumber, Constant.MISSING_CELL_POLICY);
								String cellValue = getCellValue(poiCell);
								
								if (dataRequiredList.get(cellNumber) && cellValue.trim().isEmpty()) {
									isSuccess = false;
								}
								rowMap.put(dataKeyList.get(cellNumber), cellValue);
							}
							rowMap.put(Constant.ROW_NUMBER_KEY, String.valueOf(rowNumber + 1));
							if (isSuccess) {
								successList.add(rowMap);
							} else {
								failList.add(rowMap);
							}
						}
					}
					read.setSuccessList(successList);
					read.setFailList(failList);
				}
			}
			return read;
		}
		private static final String getCellValue(Cell cell) {
			if (cell == null) {
				return Constant.CELL_VALUE_DEFAULT;
			}
			try {
				switch (cell.getCellType()) {
					case STRING:
						return FormatHelper.Util.parseString(cell.getStringCellValue());
					case NUMERIC:
						if (DateUtil.isCellDateFormatted(cell)) {
							return FormatHelper.Util.parseDate(cell.getDateCellValue());
						}
						double numeric = cell.getNumericCellValue();
						return Double.isNaN(numeric) || Double.isInfinite(numeric) ? Constant.CELL_VALUE_DEFAULT : String.valueOf(numeric == Math.floor(numeric) ? (long) numeric : numeric);
					case BOOLEAN:
						return String.valueOf(cell.getBooleanCellValue());
					default:
						return Constant.CELL_VALUE_DEFAULT;
				}
			} catch (Exception e) {
				return Constant.CELL_VALUE_DEFAULT;
			}
		}
		public static final Vo.Write parseReadToWrite(Vo.Read read) {
			return parseReadToWrite(read, null, null, null, false);
		}
		public static final Vo.Write parseReadToWrite(Vo.Read read, List<String> headerList, boolean successRequired) {
			return parseReadToWrite(read, headerList, null, null, successRequired);
		}
		public static final Vo.Write parseReadToWrite(Vo.Read read, List<String> headerList, List<Integer> cellWidthList, List<FormatHelper.Type.Format> cellTypeList, boolean successRequired) {
			List<String> dataKeyList = read.getDataKeyList();
			int dataKeySize = dataKeyList.size();
			
			List<String> copyDataKeyList = new ArrayList<>(dataKeyList);
			copyDataKeyList.add(0, Constant.ROW_NUMBER_KEY);

			List<String> copyHeaderList = new ArrayList<>(headerList != null && headerList.size() == dataKeySize ? headerList : dataKeyList);
			copyHeaderList.add(0, Constant.ROW_NUMBER_HEADER);
			
			List<Integer> copyCellWidthList = new ArrayList<>(cellWidthList != null && cellWidthList.size() == dataKeySize ? cellWidthList : Collections.nCopies(dataKeySize, Constant.CELL_WIDTH_DEFAULT));
			copyCellWidthList.add(0, Constant.ROW_NUMBER_WIDTH);
			
			List<FormatHelper.Type.Format> copyCellTypeList = new ArrayList<>(cellTypeList != null && cellTypeList.size() == dataKeySize ? cellTypeList : Collections.nCopies(dataKeySize, FormatHelper.Type.Format.STRING));
			copyCellTypeList.add(0, FormatHelper.Type.Format.INTEGER);
			
			Vo.Write.Ssheet success = new Vo.Write.Ssheet();
			Vo.Write.Ssheet fail = new Vo.Write.Ssheet();
			
			if (successRequired) {
				success.setSheetTitle(Constant.SHEET_TITLE_IS_SUCCESS + read.getSuccessCount() + Constant.SHEET_TITLE_OF_COUNT);
				success.setDataList(read.getSuccessList());
				success.setDataKeyList(copyDataKeyList);
				success.setHeaderList(copyHeaderList);
				success.setCellWidthList(copyCellWidthList);
				success.setCellTypeList(copyCellTypeList);
			}
			fail.setSheetTitle(Constant.SHEET_TITLE_IS_FAIL + read.getFailCount() + Constant.SHEET_TITLE_OF_COUNT);
			fail.setDataList(read.getFailList());
			fail.setDataKeyList(copyDataKeyList);
			fail.setHeaderList(copyHeaderList);
			fail.setCellWidthList(copyCellWidthList);
			fail.setCellTypeList(copyCellTypeList);
			
			Vo.Write write = new Vo.Write();
			
			write.setSheetList(successRequired ? Arrays.asList(success, fail) : Collections.singletonList(fail));
			write.setFileName(Constant.FILE_NAME_IS_SUCCESS_FAIL);
			write.setCellStyleRequired(true);
			
			return write;
		}
	}
	private static final class Template {
		private static final HorizontalAlignment HORIZONTAL_ALIGNMENT = HorizontalAlignment.CENTER;
		private static final VerticalAlignment VERTICAL_ALIGNMENT = VerticalAlignment.CENTER;
		
		private static final short FONT_TITLE_SIZE = 16;
		private static final short FONT_DATE_SIZE = FONT_TITLE_SIZE - 2;
		private static final short FONT_HEADER_SIZE = FONT_DATE_SIZE - 2;
		
		private static final FillPatternType FILL_PATTERN = FillPatternType.SOLID_FOREGROUND;
		private static final short FILL_COLOR_INDEX = IndexedColors.GREY_25_PERCENT.index;
		
		private static final BorderStyle BORDER_STYLE = BorderStyle.THIN;
		private static final short BORDER_COLOR_INDEX = IndexedColors.BLACK.index;
		
		private static final byte LINK_UNDER_LINE = Font.U_SINGLE;
		private static final short LINK_COLOR_INDEX = IndexedColors.BLUE.index;
		
		private static final CellStyle getTitleCellStyle(SXSSFWorkbook poiWorkbook) {
			CellStyle cellStyle = poiWorkbook.createCellStyle();
			
			cellStyle.setWrapText(true);
			cellStyle.setAlignment(HORIZONTAL_ALIGNMENT);
			cellStyle.setVerticalAlignment(VERTICAL_ALIGNMENT);
			
			cellStyle.setFont(getFont(poiWorkbook, FONT_TITLE_SIZE));
			
			return cellStyle;
		}
		private static final CellStyle getDateCellStyle(SXSSFWorkbook poiWorkbook) {
			CellStyle cellStyle = poiWorkbook.createCellStyle();
			
			cellStyle.setWrapText(true);
			cellStyle.setVerticalAlignment(VERTICAL_ALIGNMENT);
			
			cellStyle.setFont(getFont(poiWorkbook, FONT_DATE_SIZE));
			
			return cellStyle;
		}
		private static final CellStyle getHeaderCellStyle(SXSSFWorkbook poiWorkbook) {
			CellStyle cellStyle = poiWorkbook.createCellStyle();
			
			cellStyle.setAlignment(HORIZONTAL_ALIGNMENT);
			cellStyle.setVerticalAlignment(VERTICAL_ALIGNMENT);
			
			cellStyle.setWrapText(true);
			cellStyle.setFont(getFont(poiWorkbook, FONT_HEADER_SIZE));

			cellStyle.setFillPattern(FILL_PATTERN);
			cellStyle.setFillForegroundColor(FILL_COLOR_INDEX);
			
			cellStyle.setBorderTop(BORDER_STYLE);
			cellStyle.setBorderRight(BORDER_STYLE);
			cellStyle.setBorderBottom(BORDER_STYLE);
			cellStyle.setBorderLeft(BORDER_STYLE);
			
			cellStyle.setTopBorderColor(BORDER_COLOR_INDEX);
			cellStyle.setRightBorderColor(BORDER_COLOR_INDEX);
			cellStyle.setBottomBorderColor(BORDER_COLOR_INDEX);
			cellStyle.setLeftBorderColor(BORDER_COLOR_INDEX);
			
			return cellStyle;
		}
		private static final CellStyle getBodyCellStyle(SXSSFWorkbook poiWorkbook) {
			CellStyle cellStyle = poiWorkbook.createCellStyle();
			
			cellStyle.setVerticalAlignment(VERTICAL_ALIGNMENT);
			
			cellStyle.setBorderTop(BORDER_STYLE);
			cellStyle.setBorderRight(BORDER_STYLE);
			cellStyle.setBorderBottom(BORDER_STYLE);
			cellStyle.setBorderLeft(BORDER_STYLE);
			
			cellStyle.setTopBorderColor(BORDER_COLOR_INDEX);
			cellStyle.setRightBorderColor(BORDER_COLOR_INDEX);
			cellStyle.setBottomBorderColor(BORDER_COLOR_INDEX);
			cellStyle.setLeftBorderColor(BORDER_COLOR_INDEX);
			
			return cellStyle;
		}
		private static final CellStyle getLinkBodyCellStyle(SXSSFWorkbook poiWorkbook) {
			CellStyle cellStyle = poiWorkbook.createCellStyle();
			
			cellStyle.setVerticalAlignment(VERTICAL_ALIGNMENT);
			
			cellStyle.setFont(getLinkFont(poiWorkbook));
			
			cellStyle.setBorderTop(BORDER_STYLE);
			cellStyle.setBorderRight(BORDER_STYLE);
			cellStyle.setBorderBottom(BORDER_STYLE);
			cellStyle.setBorderLeft(BORDER_STYLE);
			
			cellStyle.setTopBorderColor(BORDER_COLOR_INDEX);
			cellStyle.setRightBorderColor(BORDER_COLOR_INDEX);
			cellStyle.setBottomBorderColor(BORDER_COLOR_INDEX);
			cellStyle.setLeftBorderColor(BORDER_COLOR_INDEX);
			
			return cellStyle;
		}
		private static final Font getFont(SXSSFWorkbook poiWorkbook, short fontSize) {
			Font font = poiWorkbook.createFont();
			
			font.setBold(true);
			font.setFontHeightInPoints(fontSize);
			
			return font;
		}
		private static final Font getLinkFont(SXSSFWorkbook poiWorkbook) {
			Font font = poiWorkbook.createFont();
			
			font.setUnderline(LINK_UNDER_LINE);
			font.setColor(LINK_COLOR_INDEX);
			
			return font;
		}
		private static final CellRangeAddress getCellRange(int rowNumber, int cellSize) {
			return new CellRangeAddress(rowNumber - 1, rowNumber - 1, 0, cellSize - 1);
		}
	}
}