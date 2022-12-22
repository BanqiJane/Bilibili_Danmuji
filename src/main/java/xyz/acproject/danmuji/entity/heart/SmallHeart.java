package xyz.acproject.danmuji.entity.heart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SmallHeart implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1815904721726060137L;
	private Short heartbeat_interval;
	private String secret_key;
	private short[] secret_rule;
	private Long timestamp;
	
}
