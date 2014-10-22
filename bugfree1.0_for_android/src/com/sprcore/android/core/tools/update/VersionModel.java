package com.sprcore.android.core.tools.update;

/**
 * Զ�̸���version��Ӧ��model
 * Զ�̵�version.xml
<update>
	<version>3</version>
	<name>MyBugFree v1.0.2</name>
	<url>http://192.168.1.105/MyBugFree/MyBugFree.apk</url>
</update>
 * @author chenshiming
 *
 */
public class VersionModel {

	private int version;
	private String name;
	private String url;
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}

}
