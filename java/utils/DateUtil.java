import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class DateUtil {
	private static final String DATE_TIME_PATTERN = "yyyyMMddHHmmss";
	private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);
	
	private static final int HOURS_START = LocalTime.MIN.getHour();
	private static final int HOURS_END = LocalTime.MAX.getHour();
	private static final int MINUTES_START = LocalTime.MIN.getMinute();
	private static final int MINUTES_END = LocalTime.MAX.getMinute();
	private static final int SECONDS_START = LocalTime.MIN.getSecond();
	private static final int SECONDS_END = LocalTime.MAX.getSecond();
	
	private enum TimeType {
		NORMAL {
			@Override
			LocalDateTime calculate(LocalDateTime localDateTime, ChronoUnit unit) {
				return localDateTime;
			}
		},
		START {
			@Override
			LocalDateTime calculate(LocalDateTime localDateTime, ChronoUnit unit) {
				return calculate(localDateTime, unit, HOURS_START, MINUTES_START, SECONDS_START);
			}
		},
		END {
			@Override
			LocalDateTime calculate(LocalDateTime localDateTime, ChronoUnit unit) {
				return calculate(localDateTime, unit, HOURS_END, MINUTES_END, SECONDS_END);
			}
		};
		abstract LocalDateTime calculate(LocalDateTime localDateTime, ChronoUnit unit);
		private static final LocalDateTime calculate(LocalDateTime localDateTime, ChronoUnit unit, int hour, int min, int sec) {
			switch (unit) {
				case DAYS:
					return localDateTime.withHour(hour).withMinute(min).withSecond(sec);
				case HOURS:
					return localDateTime.withMinute(min).withSecond(sec);
				case MINUTES:
					return localDateTime.withSecond(sec);
				default:
					return localDateTime;
			}
		}
	}
	public static final String getByMinutes(int min) {
		return calculate(min, ChronoUnit.MINUTES, TimeType.NORMAL);
	}
	public static final String getStartByMinutes(int min) {
		return calculate(min, ChronoUnit.MINUTES, TimeType.START);
	}
	public static final String getEndByMinutes(int min) {
		return calculate(min, ChronoUnit.MINUTES, TimeType.END);
	}
	public static final String getByHours(int hour) {
		return calculate(hour, ChronoUnit.HOURS, TimeType.NORMAL);
	}
	public static final String getStartByHours(int hour) {
		return calculate(hour, ChronoUnit.HOURS, TimeType.START);
	}
	public static final String getEndByHours(int hour) {
		return calculate(hour, ChronoUnit.HOURS, TimeType.END);
	}
	public static final String getByDays(int day) {
		return calculate(day, ChronoUnit.DAYS, TimeType.NORMAL);
	}
	public static final String getStartByDays(int day) {
		return calculate(day, ChronoUnit.DAYS, TimeType.START);
	}
	public static final String getEndByDays(int day) {
		return calculate(day, ChronoUnit.DAYS, TimeType.END);
	}
	private static final String calculate(int val, ChronoUnit unit, TimeType type) {
		return format(type.calculate(LocalDateTime.now().plus(val, unit), unit));
	}
	private static final String format(LocalDateTime localDateTime) {
		return localDateTime.format(DATE_TIME_FORMATTER);
	}
}