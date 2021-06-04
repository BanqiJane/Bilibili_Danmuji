package xyz.acproject.danmuji.component;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.config.CronTask;
import org.springframework.stereotype.Component;
import xyz.acproject.danmuji.task.ScheduledTask;
import xyz.acproject.danmuji.utils.SchedulingRunnableUtil;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName TaskRegisterComponent
 * @Description TODO
 * @author BanqiJane
 * @date 2020年9月5日 下午11:52:54
 *
 * @Copyright:2020 blogs.acproject.xyz Inc. All rights reserved.
 */
@Component
public class TaskRegisterComponent implements DisposableBean{

	private final Map<SchedulingRunnableUtil, ScheduledTask> scheduledTasks = new ConcurrentHashMap<>(6);

	private TaskScheduler taskScheduler;
	
	
	
	
	public TaskScheduler getTaskScheduler() {
		return this.taskScheduler;
	}
	/**
	 * 添加任务
	 * 
	 * @param task
	 * @param expression
	 */
	public void addTask(SchedulingRunnableUtil task,String expression) {
		addTask(task,new CronTask(task, expression));
	}
	
	public void addTask(SchedulingRunnableUtil task,CronTask cronTask) {
		if(cronTask!=null) {
			if(!this.scheduledTasks.containsKey(task)) {
				this.scheduledTasks.put(task,scheduledTask(cronTask));
			}
		}
	}

	public boolean hasTask(Runnable task){
		return this.scheduledTasks.containsKey(task);
	}
	
	/**
	 * 移除指定任务
	 * @param task
	 */
	public void removeTask(Runnable task) {
		ScheduledTask scheduledTask = this.scheduledTasks.remove(task);
		if(scheduledTask!=null) {
			scheduledTask.cancel();
		}
	}

	public Map<SchedulingRunnableUtil, ScheduledTask> getScheduledTasks(){
		return this.scheduledTasks;
	}

	
	public ScheduledTask scheduledTask(CronTask cronTask) {
		  ScheduledTask scheduledTask = new ScheduledTask();
		  scheduledTask.future = this.taskScheduler.schedule(cronTask.getRunnable(), cronTask.getTrigger());
		  return scheduledTask;
	}
	
	public int getTaskSize() {
		return this.scheduledTasks.size();
	}
	/**
	 * 清除所有任务
	 */
	@Override
	public void destroy() throws Exception {
		// TODO 自动生成的方法存根
		for (ScheduledTask task : this.scheduledTasks.values()) {
			task.cancel();
		}
		this.scheduledTasks.clear();
	}

	@Autowired
	public void setTaskScheduler(TaskScheduler taskScheduler) {
		this.taskScheduler = taskScheduler;
	}
}
