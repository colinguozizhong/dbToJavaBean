package dbRobot;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;


public class SqlUtil {


	public SqlUtil() {
		// TODO Auto-generated constructor stub
	}
	//创建.xml文件
	public String createBean(String tbName, List<Map<String, String>> collist,
			Map<String, String> infoMap) {
		
		StringBuilder classInfo = new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		classInfo.append("\r\n\t");
		classInfo.append("<!DOCTYPE sqlMap PUBLIC \"-//ibatis.apache.org//DTD SQL Map 2.0//EN\" \"http://ibatis.apache.org/dtd/sql-map-2.dtd\">");
		classInfo.append("\r\n");
		classInfo.append("<sqlMap namespace=\"").append(upperFirestChar(tbName)).append("\">");
		classInfo.append("\r\n\t");
		classInfo.append("<typeAlias alias=\"xx此处自定义xx\" type=\"JavaBean路径：com.ahzj.sampleVO\"/>");
		classInfo.append("\r\n\t");
		// 写 插入语句
		classInfo.append("<select id=\"queryAll\" parameterClass=\"改为和typeAlias一样\" resultClass=\"改为和typeAlias一样\"");
		classInfo.append("\">");
		classInfo.append("\r\n\t");
		classInfo.append(genFindAllSQL(tbName,collist));
		classInfo.append("\r\n\t");
		classInfo.append("</select>");
		
		//写入新增语句
		classInfo.append("\r\n\t");
		classInfo.append("<-- 功能说明：插入语句-->");
		classInfo.append("\r\n\t");
		classInfo.append("<insert id=\"insertInto\" parameterClass=\"改为和typeAlias一样\"");
		classInfo.append("\">");
		classInfo.append("\r\n\t");
		classInfo.append(insertSql(tbName,collist));
		classInfo.append("\r\n\t");
		classInfo.append("</insert>");
		
		//写入更新语句
		classInfo.append("\r\n\t");
		classInfo.append("<-- 功能说明：更新语句-->");
		classInfo.append("\r\n\t");
		classInfo.append("<update id=\"updateByPrimaryId\" parameterClass=\"改为和typeAlias一样\"");
		classInfo.append("\">");
		classInfo.append("\r\n\t");
		classInfo.append(updateSql(tbName,collist));
		classInfo.append("</update>");
		
		File file = new File(infoMap.get("catName"), upperFirestChar(upperFirestChar(tbName).toLowerCase()) + ".xml");
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
	
	/**
	 * 查询所有sql
	 * @param tableName
	 * @param collist
	 * @return
	 */
	public static StringBuilder genFindAllSQL(String tableName,List<Map<String, String>> collist){
		StringBuilder sql = new StringBuilder();
		sql.append("\t\t");
		sql.append("select ");
		sql.append("\r\n\t\t");
		long i = 0;
		for (Map<String, String> colmap : collist) {
			String field = colmap.get("filed").toString();
			if(i != 0){
				sql.append("\t\t");
				sql.append(",").append(field.toUpperCase()+ " ").append("as"+" ").append(field.toLowerCase());
				sql.append("\r\n\t\t");
			}else{
				sql.append("\t\t");
				sql.append(field.toUpperCase()+ " ").append("as"+" ").append(field.toLowerCase());
				sql.append("\r\n\t\t");
			}
			i++;
		}
		sql.append("\r\t");
		sql.append("from ").append(tableName);
		return sql; 
	}
	
	/**
	 * 新增sql
	 * @param tableName
	 * @param collist
	 * @return
	 */
	public static StringBuilder insertSql(String tableName,List<Map<String, String>> collist){
		StringBuilder sql = new StringBuilder();
		sql.append("\t");
		sql.append("insert into ").append(tableName);
		sql.append("\r\n\t\t");
		sql.append("<dynamic prepend=\"(\">");
		for (Map<String, String> colmap : collist) {
			String field = colmap.get("filed").toString();
			sql.append("\r\n\t\t\t");
			sql.append("<isNotNull prepend=\",\" property=\"").append(field.toLowerCase()).append("\">");
			sql.append("\r\n\t\t\t\t\t");
			sql.append(field.toUpperCase());
			sql.append("\r\n\t\t\t");
			sql.append("</isNotNull>");
			sql.append("\r\t");
		}
		sql.append("\r\n\t\t");
		sql.append("</dynamic>");
		sql.append("\r\n\t\t");
		sql.append(")");
		sql.append("values");
		sql.append("\r\n\t\t");
		sql.append("<dynamic prepend=\"(\">");
		for (Map<String, String> colmap : collist) {
			String field = colmap.get("filed").toString();
			sql.append("\r\n\t\t\t");
			sql.append("<isNotNull prepend=\",\" property=\"").append(field.toLowerCase()).append("\">");
			sql.append("\r\n\t\t\t\t\t");
			sql.append("#").append(field.toLowerCase()).append("#");
			sql.append("\r\n\t\t\t");
			sql.append("</isNotNull>");
			sql.append("\r\t");
		}
		sql.append("\r\n\t\t");
		sql.append("</dynamic>");
		sql.append("\r\n\t\t");
		sql.append(")");
		return sql; 
	}
	/**
	 * 更新语句
	 * @param src
	 * @return
	 */
	public static StringBuilder updateSql(String tableName,List<Map<String, String>> collist){
		StringBuilder sql = new StringBuilder();
		sql.append("\t");
		sql.append("update ").append(tableName);
		sql.append("\t");
		sql.append("set");
		sql.append("\r\n\t\t");
		long i = 0;
		for (Map<String, String> colmap : collist) {
			String field = colmap.get("filed").toString();
			if(i != 0){
				sql.append("\t");
				sql.append("<isNotNull prepend=\",\" property=\"").append(field.toLowerCase()).append("\">");
				sql.append("\r\n\t\t\t\t");
				sql.append(field.toUpperCase());
				sql.append("=");
				sql.append("#").append(field.toLowerCase()).append("#");
				sql.append("\r\n\t\t");
				sql.append("</isNotNull>");
				sql.append("\r\t\t");
			}else{
				sql.append("<isNotNull prepend=\"\" property=\"").append(field.toLowerCase()).append("\">");
				sql.append("\r\n\t\t\t\t");
				sql.append(field.toUpperCase());
				sql.append("=");
				sql.append("#").append(field.toLowerCase()).append("#");
				sql.append("\r\n\t\t");
				sql.append("</isNotNull>");
				sql.append("\r\t\t");
			}
			sql.append("\r\n\t");
			i++;
		}
		return sql;
	}

	//首字母大写
	public String upperFirestChar(String src) {
		return src.substring(0, 1).toUpperCase().concat(src.substring(1));
	}

	//获取字段
	@SuppressWarnings("unused")
	private String getFieldStr(String field, String type) {
		StringBuilder sb = new StringBuilder();
		sb.append("\t").append("private ").append(type).append(" ")
				.append(field).append(";");
		sb.append("\r\n");
		return sb.toString();
	}

}
