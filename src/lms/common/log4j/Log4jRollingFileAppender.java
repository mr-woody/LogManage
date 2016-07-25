package lms.common.log4j;

import java.io.File;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.RollingFileAppender;
import org.apache.log4j.helpers.CountingQuietWriter;
import org.apache.log4j.spi.LoggingEvent;

public class Log4jRollingFileAppender extends RollingFileAppender {

	private long nextRollover = 0;
	protected static final Log LogLog = LogFactory.getLog(Log4jRollingFileAppender.class);
	public void rollOver() {
		File target;
		File file;

		if (qw != null) {
			long size = ((CountingQuietWriter) qw).getCount();
			LogLog.debug("rolling over count=" + size);
			nextRollover = size + maxFileSize;
		}
		LogLog.debug("maxBackupIndex=" + maxBackupIndex);

		boolean renameSucceeded = true;
		if (maxBackupIndex > 0) {
			file = new File(getRollingFileName(fileName, maxBackupIndex));
			if (file.exists())
				renameSucceeded = file.delete();

			for (int i = maxBackupIndex - 1; i >= 1 && renameSucceeded; i--) {
				file = new File(getRollingFileName(fileName, i));
				if (file.exists()) {
					target = new File(getRollingFileName(fileName, i + 1));
					LogLog.debug("Renaming file " + file + " to " + target);
					renameSucceeded = file.renameTo(target);
				}
			}

			if (renameSucceeded) {
				target = new File(this.getRollingFileName(fileName, 1));

				this.closeFile();

				file = new File(fileName);
				LogLog.debug("Renaming file " + file + " to " + target);
				renameSucceeded = file.renameTo(target);
				if (!renameSucceeded) {
					try {
						this.setFile(fileName, true, bufferedIO, bufferSize);
					} catch (IOException e) {
						if (e instanceof InterruptedIOException) {
							Thread.currentThread().interrupt();
						}
						LogLog.error("setFile(" + fileName
								+ ", true) call failed.", e);
					}
				}
			}
		}
		if (renameSucceeded) {
			try {
				this.setFile(fileName, false, bufferedIO, bufferSize);
				nextRollover = 0;
			} catch (IOException e) {
				if (e instanceof InterruptedIOException) {
					Thread.currentThread().interrupt();
				}
				LogLog.error("setFile(" + fileName + ", false) call failed.", e);
			}
		}
	}

	private String getRollingFileName(String fileName, int index) {
		Pattern p = Pattern.compile("_\\d+\\.");
		Matcher m = p.matcher(fileName);

		return m.replaceFirst(String.format("_%d.", index));
	}

	public synchronized void setFile(String fileName, boolean append,
			boolean bufferedIO, int bufferSize) throws IOException {
		Date curDate = new Date();
		String temp = String.format(fileName, curDate);
		if (!temp.startsWith("/")) {
			super.setFile(temp, append, bufferedIO, bufferSize);
		}
	}

	protected void subAppend(LoggingEvent event) {
		super.subAppend(event);
		if (fileName != null && qw != null) {
			long size = ((CountingQuietWriter) qw).getCount();
			if (size >= maxFileSize && size >= nextRollover) {
				rollOver();
			}
		}
	}

}
