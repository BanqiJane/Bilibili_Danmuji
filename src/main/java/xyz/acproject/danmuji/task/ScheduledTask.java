package xyz.acproject.danmuji.task;

import java.util.concurrent.ScheduledFuture;

public final class ScheduledTask {
	public volatile ScheduledFuture<?> future;
	
	/**
	 * 取消定时任务
	 */
	public void cancel() {
		ScheduledFuture<?> future = this.future;
		if(future!=null) {
			future.cancel(true);
		}
	}
}
