package dbRobot;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BeanUtil {

	private String humpField(String field){
		field = field.toLowerCase();
		Pattern p=Pattern.compile("_");
		Matcher matcher=p.matcher(field);
		while(matcher.find()){
			if(matcher.start()<field.length()-1 && matcher.start() > 0){
				// 如果不是最后一个字符 且从第二个开始替换为大写字母
				StringBuffer buffer = new StringBuffer(field);
			}
			System.out.println(matcher.start());
		}
		return field;
	}

	public BeanUtil() {
		// TODO Auto-generated constructor stub
	}
	//创建JavaBean文件
	public String createBean(String tbName, List<Map<String, String>> collist,
			Map<String, String> infoMap) {
		
		StringBuilder fields = new StringBuilder();
		StringBuilder methods = new StringBuilder();

		StringBuilder classInfo = new StringBuilder("\t/**\r\n\t*");
		for (Map<String, String> colmap : collist) {
			String field = colmap.get("filed").toString();
			//humpField(field);
			String type = typeTrans(colmap.get("type").toString());
			fields.append(getFieldStr(field.toLowerCase(), type));
			methods.append(getMethodStr(field.toLowerCase(), type));

		}
		classInfo.append("\t*@author 凌佳佳");
		classInfo.append("\r\n\t*/\r\n\r\n");
		classInfo.append("\tpublic class ").append(upperFirestChar(tbName))
				.append("{\r\n");
		classInfo.append(fields);
		classInfo.append("\r\n");
		classInfo.append(methods);
		classInfo.append("\r\n");
		classInfo.append("}");
		File file = new File(infoMap.get("catName"), upperFirestChar(upperFirestChar(tbName).toLowerCase()) + "VO.java");
		try {
			FileWriter fw = new FileWriter(file);
			if (infoMap.get("packName") == null || infoMap.get("packName").toString().equals("")) {
				
			} else {
				String packageinfo =  "package " + infoMap.get("packName").toString() + ";\r\n\r\n";
				fw.write(packageinfo);
			}
			
			fw.write(classInfo.toString());
			fw.flush();
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	//数据库字段类型与JAVA类型转换
	public String typeTrans(String type) {
		if (type.contains("tinyint")) {
			return "boolean";
		} else if (type.contains("NUMBER")||type.contains("INTEGER")) {
			return "int";
		} else if (type.contains("TIMESTAMP")) {
			return "Date";
		} else if (type.contains("BIGINT")) {
			return "Long";
		} else if (type.contains("varchar") 
				|| type.contains("VARCHAR2")) {
			return "String";
		} else if (type.contains("binary") || type.contains("blob")) {
			return "byte[]";
		} else {
			return "String";
		}
	}
	//获取方法字符串 
	private String getMethodStr(String field, String type) {
		StringBuilder get = new StringBuilder("\tpublic ");
		get.append(type).append(" ");
		if (type.equals("boolean")) {
			get.append(field);
		} else {
			get.append("get");
			get.append(upperFirestChar(field));
		}
		get.append("(){").append("\r\n\t\treturn this.").append(field)
				.append(";\r\n\t}\r\n");
		StringBuilder set = new StringBuilder("\tpublic void ");

		if (type.equals("boolean")) {
			set.append(field);
		} else {
			set.append("set");
			set.append(upperFirestChar(field));
		}
		set.append("(").append(type).append(" ").append(field)
				.append("){\r\n\t\tthis.").append(field).append("=")
				.append(field).append(";\r\n\t}\r\n");
		get.append(set);
		return get.toString();
	}

	//首字母大写
	public String upperFirestChar(String src) {
		return src.substring(0, 1).toUpperCase().concat(src.substring(1));
	}

	//获取字段
	private String getFieldStr(String field, String type) {
		StringBuilder sb = new StringBuilder();
		sb.append("\t").append("private ").append(type).append(" ")
				.append(field).append(";");
		sb.append("\r\n");
		return sb.toString();
	}

}
