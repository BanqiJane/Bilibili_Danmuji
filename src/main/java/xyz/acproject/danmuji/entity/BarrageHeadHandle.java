package xyz.acproject.danmuji.entity;

import struct.StructClass;
import struct.StructField;

import java.io.Serializable;

/**
 * @ClassName BarrageHeadHandle
 * @Description TODO
 * @author BanqiJane
 * @date 2020年8月10日 下午12:27:54
 *
 * @Copyright:2020 blogs.acproject.xyz Inc. All rights reserved.
 */
@StructClass
public class BarrageHeadHandle implements Serializable,Cloneable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8552333200751773861L;
	private static BarrageHeadHandle barrageHeadHandle = new BarrageHeadHandle();
	// 数据包长度 为int
	@StructField(order = 0)
	private int packageLength;
	// 数据包头部长度 为char 为16
	@StructField(order = 1)
	private char packageHeadLength;
	// 数据包协议版本char 0未压缩的json格式数据 1客户端心跳通常为人气值 4字节整数 2为带zlib压缩过的json格式数据 数据包协议版本 为char 有0，1，2
	@StructField(order = 2)
	private char packageVersion;
	// 数据包协议类型 int 目前已知有2，3，5，7，8
	@StructField(order = 3)
	private int packageType;
	//序列号 int 目前已知有0，1
	@StructField(order = 4)
	private int packageOther;
	private BarrageHeadHandle() {}

	public BarrageHeadHandle(int packageLength, char packageHeadLength, char packageVersion, int packageType, int packageOther) {
		this.packageLength = packageLength;
		this.packageHeadLength = packageHeadLength;
		this.packageVersion = packageVersion;
		this.packageType = packageType;
		this.packageOther = packageOther;
	}

	//复制模式
	public static BarrageHeadHandle getBarrageHeadHandle() {
		try {
			return (BarrageHeadHandle)barrageHeadHandle.clone();
		} catch (CloneNotSupportedException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return new BarrageHeadHandle();
	}

//单例模式
//    public static BarrageHeadHandle getBarrageHeadHandle() {
//        if (barrageHeadHandle == null) {
//            synchronized (BarrageHeadHandle.class) {
//                barrageHeadHandle = new BarrageHeadHandle();
//            }
//        }
//        return barrageHeadHandle;
//    }

	public static BarrageHeadHandle getBarrageHeadHandle(int packageLength, char packageHeadLength, char packageVersion, int packageType, int packageOther) {
		try {
			BarrageHeadHandle b = (BarrageHeadHandle)barrageHeadHandle.clone();
			b.setPackageHeadLength(packageHeadLength);
			b.setPackageLength(packageLength);
			b.setPackageOther(packageOther);
			b.setPackageType(packageType);
			b.setPackageVersion(packageVersion);
			return b;
		} catch (CloneNotSupportedException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return new BarrageHeadHandle(packageLength,packageHeadLength,packageVersion,packageType,packageOther);
	}

	//    public static BarrageHeadHandle getBarrageHeadHandle(int packageLength, char packageHeadLength, char packageVersion, int packageType, int packageOther) {
//        if (barrageHeadHandle == null) {
//            synchronized (BarrageHeadHandle.class) {
//                barrageHeadHandle = new BarrageHeadHandle();
//            }
//        }
//        barrageHeadHandle.setPackageHeadLength(packageHeadLength);
//        barrageHeadHandle.setPackageLength(packageLength);
//        barrageHeadHandle.setPackageOther(packageOther);
//        barrageHeadHandle.setPackageType(packageType);
//        barrageHeadHandle.setPackageVersion(packageVersion);
//        return barrageHeadHandle;
//    }
	public int getPackageLength() {
		return packageLength;
	}
	public void setPackageLength(int packageLength) {
		this.packageLength = packageLength;
	}
	public char getPackageHeadLength() {
		return packageHeadLength;
	}
	public void setPackageHeadLength(char packageHeadLength) {
		this.packageHeadLength = packageHeadLength;
	}
	public char getPackageVersion() {
		return packageVersion;
	}
	public void setPackageVersion(char packageVersion) {
		this.packageVersion = packageVersion;
	}
	public int getPackageType() {
		return packageType;
	}
	public void setPackageType(int packageType) {
		this.packageType = packageType;
	}
	public int getPackageOther() {
		return packageOther;
	}
	public void setPackageOther(int packageOther) {
		this.packageOther = packageOther;
	}
	
}
