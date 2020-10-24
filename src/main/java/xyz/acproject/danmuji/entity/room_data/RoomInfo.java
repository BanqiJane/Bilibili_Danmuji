package xyz.acproject.danmuji.entity.room_data;

import java.io.Serializable;

/**
 * @ClassName RoomInfo
 * @Description TODO
 * @author BanqiJane
 * @date 2020年8月11日 上午1:49:49
 *
 * @Copyright:2020 blogs.acproject.xyz Inc. All rights reserved.
 */
public class RoomInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 782231567104445143L;
	private Long uid;
	private Integer short_id;
	private Long room_id;
	private String title;
	private Short live_status;
	private Long live_start_time;
	private Short lock_status;
	private Long lock_time;
	private Integer area_id;
	private Integer parent_area_id;
	private String parent_area_name;
	private String area_name;
	
	public RoomInfo() {
		super();
		// TODO 自动生成的构造函数存根
	}

	
	
	public RoomInfo(Long uid, Integer short_id, Long room_id, String title, Short live_status, Long live_start_time,
			Short lock_status, Long lock_time, Integer area_id, Integer parent_area_id, String parent_area_name,
			String area_name) {
		super();
		this.uid = uid;
		this.short_id = short_id;
		this.room_id = room_id;
		this.title = title;
		this.live_status = live_status;
		this.live_start_time = live_start_time;
		this.lock_status = lock_status;
		this.lock_time = lock_time;
		this.area_id = area_id;
		this.parent_area_id = parent_area_id;
		this.parent_area_name = parent_area_name;
		this.area_name = area_name;
	}



	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public Integer getShort_id() {
		return short_id;
	}

	public void setShort_id(Integer short_id) {
		this.short_id = short_id;
	}

	public Long getRoom_id() {
		return room_id;
	}

	public void setRoom_id(Long room_id) {
		this.room_id = room_id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Short getLive_status() {
		return live_status;
	}

	public void setLive_status(Short live_status) {
		this.live_status = live_status;
	}

	public Long getLive_start_time() {
		return live_start_time;
	}

	public void setLive_start_time(Long live_start_time) {
		this.live_start_time = live_start_time;
	}

	public Short getLock_status() {
		return lock_status;
	}

	public void setLock_status(Short lock_status) {
		this.lock_status = lock_status;
	}

	public Long getLock_time() {
		return lock_time;
	}

	public void setLock_time(Long lock_time) {
		this.lock_time = lock_time;
	}

	public Integer getArea_id() {
		return area_id;
	}

	public void setArea_id(Integer area_id) {
		this.area_id = area_id;
	}

	public Integer getParent_area_id() {
		return parent_area_id;
	}

	public void setParent_area_id(Integer parent_area_id) {
		this.parent_area_id = parent_area_id;
	}

	public String getParent_area_name() {
		return parent_area_name;
	}

	public void setParent_area_name(String parent_area_name) {
		this.parent_area_name = parent_area_name;
	}

	public String getArea_name() {
		return area_name;
	}

	public void setArea_name(String area_name) {
		this.area_name = area_name;
	}



	@Override
	public String toString() {
		return "RoomInfo [uid=" + uid + ", short_id=" + short_id + ", room_id=" + room_id + ", title=" + title
				+ ", live_status=" + live_status + ", live_start_time=" + live_start_time + ", lock_status="
				+ lock_status + ", lock_time=" + lock_time + ", area_id=" + area_id + ", parent_area_id="
				+ parent_area_id + ", parent_area_name=" + parent_area_name + ", area_name=" + area_name + "]";
	}
	
	
}
