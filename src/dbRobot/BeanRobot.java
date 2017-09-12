package dbRobot;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

public class BeanRobot extends JFrame {
	private static final long serialVersionUID = 1L;
	private JTextField ipFiled;
	private JTextField dbFiled;
	private JTextField dbNameFiled;
	private JTextField tabField;
	private JTextField packField;
	private JTextField catField;
	private JCheckBox checkBox;
	private JTextField userField;
	private JTextField pwdField;
	private JComboBox dbBox;
	DbUtil dbutil = new DbUtil();
	BeanUtil butil = new BeanUtil();
	SqlUtil sqltil = new SqlUtil();
	JLabel labelInfo;
	private JTable jtable;
	private MyTableModel tableModel;
	@SuppressWarnings("rawtypes")
	HashMap dbInfoMap;
	String[] titles = { "选择", "表格名称" };

	// 配置文件信息
	Map<String, HashMap<String, String>> dbMap;

	public BeanRobot() {

		setTitle("根据数据库表结构生成Bean和Sql");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 544, 374);
		setIconImage(getIconImage());

		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);

		JLabel lblIp = new JLabel("IP:");
		lblIp.setBounds(110, 13, 30, 15);
		panel.add(lblIp);

		ipFiled = new JTextField();
		// ipFiled.setText("localhost");
		ipFiled.setText("10.55.6.65:1521");
		ipFiled.setBounds(146, 10, 147, 21);
		panel.add(ipFiled);
		ipFiled.setColumns(10);

		JLabel label = new JLabel("数据库:");
		label.setBounds(80, 41, 54, 15);
		panel.add(label);

		// String dbStyles[] = { };
		// dbBox = new JComboBox(dbStyles);
		dbBox = new JComboBox();
		dbBox.setBounds(146, 39, 147, 21);
		dbBox.setVisible(true);
		dbBox.setMaximumRowCount(3);
		panel.add(dbBox);

		JLabel dbNamelabel = new JLabel("数据库名:");
		dbNamelabel.setBounds(70, 67, 60, 20);
		panel.add(dbNamelabel);

		dbNameFiled = new JTextField();
		dbNameFiled.setBounds(146, 68, 147, 21);
		// dbNameFiled.setText("test");
		dbNameFiled.setText("orcl");
		panel.add(dbNameFiled);
		dbNameFiled.setColumns(10);

		JLabel userLabel = new JLabel("用户名:");
		userLabel.setBounds(80, 98, 54, 15);
		panel.add(userLabel);

		userField = new JTextField();
		// userField.setText("root");
		userField.setText("ahzj");
		userField.setBounds(145, 97, 148, 21);
		panel.add(userField);
		userField.setColumns(10);

		JLabel pwdLabel = new JLabel("密码:");
		pwdLabel.setBounds(95, 129, 54, 15);
		panel.add(pwdLabel);

		pwdField = new JTextField();
		// pwdField.setText("root");
		pwdField.setText("ahzj");
		pwdField.setBounds(145, 126, 147, 21);
		panel.add(pwdField);
		pwdField.setColumns(10);

		JLabel packLabel = new JLabel("包名:");
		packLabel.setBounds(95, 160, 54, 15);
		panel.add(packLabel);

		packField = new JTextField();
		packField.setText("");
		packField.setBounds(146, 155, 147, 21);
		panel.add(packField);
		packField.setColumns(10);

		JLabel catlogLabel = new JLabel("输出目录：");
		catlogLabel.setBounds(70, 193, 65, 15);
		panel.add(catlogLabel);

		catField = new JTextField();
		catField.setBounds(146, 190, 147, 21);
		panel.add(catField);
		catField.setColumns(10);

		checkBox = new JCheckBox("生成包结构目录");
		checkBox.setSelected(true);
		checkBox.setBounds(145, 220, 147, 23);
		panel.add(checkBox);

		JLabel mustdbLabel = new JLabel("* 选择数据库");
		mustdbLabel.setForeground(Color.RED);
		mustdbLabel.setBounds(303, 39, 176, 15);
		panel.add(mustdbLabel);
		JLabel mustIPLabel = new JLabel("* IP地址及端口号");
		mustIPLabel.setForeground(Color.RED);
		mustIPLabel.setBounds(303, 13, 176, 15);
		panel.add(mustIPLabel);

		JLabel mustPacklabel = new JLabel("* 包结构");
		mustPacklabel.setForeground(Color.RED);
		mustPacklabel.setBounds(303, 155, 79, 15);
		panel.add(mustPacklabel);

		JLabel catlabel = new JLabel("默认D:// ；注意格式");
		catlabel.setForeground(Color.RED);
		catlabel.setBounds(303, 193, 179, 15);
		panel.add(catlabel);

		JButton button = new JButton("查询");
		// 按钮增加动作执行go()方法
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				go();

			}
		});
		button.setBounds(100, 267, 93, 23);
		panel.add(button);

		JButton crButton = new JButton("一键生成Bean和sql");
		// 按钮增加动作执行go()方法
		crButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				create();
				createSql();

			}
		});
		crButton.setBounds(200, 267, 153, 23);
		panel.add(crButton);

		/*JButton sqlButton = new JButton("生成Sql");
		// 按钮增加动作执行go()方法
		sqlButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createSql();

			}
		});
		sqlButton.setBounds(300, 267, 93, 23);
		panel.add(sqlButton);*/

		// 增加关闭事件监听，关闭相关操作
		this.addWindowListener(new WindowAdapter() {

			public void windowClosing(WindowEvent e) {
				super.windowClosing(e);
				close();
				System.exit(0);
			}
		});
		
		// 设置表格
		Object[][] tableData = {};
		tableModel = new MyTableModel(tableData, titles);
		jtable = new JTable(this.tableModel);
		TableColumn column = jtable.getColumnModel().getColumn(0);
		column.setPreferredWidth(30); // 设置第一列的宽度30
		JScrollPane scr = new JScrollPane(this.jtable);
		scr.setBounds(430, 10, 200, 290);
		panel.add(scr);
		// 添加标格监听事件
		jtable.addMouseListener(new MouseAdapter() {
			@SuppressWarnings("unused")
			public void mouseClicked(MouseEvent e) {
				int row = jtable.getSelectedRow();
				if (jtable.getSelectedColumn() == 0)// 如果是第一列的单元格，则返回，不响应点击
					return;
				// 列响应操作
			}
		});

		// 显示操作信息label
		labelInfo = new JLabel("");
		labelInfo.setForeground(Color.RED);
		labelInfo.setBounds(20, 317, 600, 60);
		panel.add(labelInfo);

		// 初始化配置信息和数据库下拉列表
		dbMap = dbutil.getDbConfigMap();
		for (String key : dbMap.keySet()) {
			this.getDbBox().addItem(key);
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// 创建对象
		BeanRobot dtb = new BeanRobot();
		// 设置可见
		dtb.setVisible(true);
		// 点击X关闭窗口
		dtb.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// 调用设置居中显示
		dtb.setSizeAndCentralizeMe(680, 440);

	}

	// 设置居中
	private void setSizeAndCentralizeMe(int width, int height) {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setSize(width, height);
		this.setLocation(screenSize.width / 2 - width / 2, screenSize.height
				/ 2 - height / 2);
	}

	@SuppressWarnings("unchecked")
	public void create() {
		String sucessName = "";
		this.getLabelInfo().setText("");
		initInfo();
		// 是否勾选生成目录标识
		boolean createPackage = this.getCheckBox().getSelectedObjects() != null;

		// 判断目录是否存在
		if (dbInfoMap.get("catName") != null) {
			if (dbInfoMap.get("catName").toString().equals("")) {
				dbInfoMap.put("catName", "D://");
			}
			if ((new File(dbInfoMap.get("catName").toString()).isDirectory()) != true) {
				this.getLabelInfo().setText("目录不存在，请重新输入");
			} else {
				if (createPackage) {
					if (dbInfoMap.get("packName") != null
							&& !dbInfoMap.get("packName").toString().equals("")) {
						String catPack = dbInfoMap.get("catName").toString()
								+ dbInfoMap.get("packName").toString();
						catPack = catPack.replace(".", "/");
						new File(catPack).mkdirs();
						dbInfoMap.put("catName", catPack);
					}
					// 勾选
					int rowCount = this.jtable.getRowCount();
					for (int i = 0; i < rowCount; i++) {
						if (this.jtable.getValueAt(i, 0).toString()
								.equals("true")) {
							String tabName = this.jtable.getValueAt(i, 1)
									.toString();
							List<Map<String, String>> tabFileds = dbutil
									.getColumnNames(dbInfoMap, tabName);
							butil.createBean(tabName, tabFileds, dbInfoMap);
							sucessName += tabName + ",";
						}
					}
				} else {
					int rowCount = this.jtable.getRowCount();
					for (int i = 0; i < rowCount; i++) {
						if (this.jtable.getValueAt(i, 0).toString()
								.equals("true")) {
							String tabName = this.jtable.getValueAt(i, 1)
									.toString();
							List<Map<String, String>> tabFileds = dbutil
									.getColumnNames(dbInfoMap, tabName);
							butil.createBean(tabName, tabFileds, dbInfoMap);
							sucessName += tabName + ",";
						}
					}

				}
				this.getLabelInfo().setText("表" + sucessName + "生成成功");
			}
		}
	}

	@SuppressWarnings({ "unchecked" })
	public void createSql() {
		String sucessName = "";
		this.getLabelInfo().setText("");
		initInfo();
		// 是否勾选生成目录标识
		boolean createPackage = this.getCheckBox().getSelectedObjects() != null;

		// 判断目录是否存在
		if (dbInfoMap.get("catName") != null) {
			if (dbInfoMap.get("catName").toString().equals("")) {
				dbInfoMap.put("catName", "D://");
			}
			if ((new File(dbInfoMap.get("catName").toString()).isDirectory()) != true) {
				this.getLabelInfo().setText("目录不存在，请重新输入");
			} else {
				if (createPackage) {
					if (dbInfoMap.get("packName") != null
							&& !dbInfoMap.get("packName").toString().equals("")) {
						String catPack = dbInfoMap.get("catName").toString()
								+ dbInfoMap.get("packName").toString();
						catPack = catPack.replace(".", "/");
						new File(catPack).mkdirs();
						dbInfoMap.put("catName", catPack);
					}
					// 勾选
					int rowCount = this.jtable.getRowCount();
					for (int i = 0; i < rowCount; i++) {
						if (this.jtable.getValueAt(i, 0).toString()
								.equals("true")) {
							String tabName = this.jtable.getValueAt(i, 1)
									.toString();
							List<Map<String, String>> tabFileds = dbutil
									.getColumnNames(dbInfoMap, tabName);
							sqltil.createBean(tabName, tabFileds, dbInfoMap);
							sucessName += tabName + ",";
						}
					}
				} else {
					int rowCount = this.jtable.getRowCount();
					for (int i = 0; i < rowCount; i++) {
						if (this.jtable.getValueAt(i, 0).toString()
								.equals("true")) {
							String tabName = this.jtable.getValueAt(i, 1)
									.toString();
							List<Map<String, String>> tabFileds = dbutil
									.getColumnNames(dbInfoMap, tabName);
							sqltil.createBean(tabName, tabFileds, dbInfoMap);
							sucessName += tabName + ",";
						}
					}

				}
				this.getLabelInfo().setText("表" + sucessName + "生成成功");
			}
		}
	}

	@SuppressWarnings("unchecked")
	public void go() {
		this.getLabelInfo().setText("");
		initInfo();
		@SuppressWarnings("unused")
		String selTableStr = dbInfoMap.get("showTable").toString();
		// 获取表名
		List<String> tableList = dbutil.getTableNames(dbInfoMap,
				dbInfoMap.get("dbName").toString());
		if (tableList == null) {
			int rowCount = this.getTableModel().getRowCount();
			int delInd = 0;
			while (delInd < rowCount) {
				this.getTableModel().removeRow(0);
				delInd++;
			}
			this.getLabelInfo().setText("数据库连接异常");
		} else {
			int rowCount = this.getTableModel().getRowCount();
			int delInd = 0;
			while (delInd < rowCount) {
				this.getTableModel().removeRow(0);
				delInd++;
			}
			for (String tName : tableList) {
				Object[] rowData = { new Boolean(false), tName };
				this.getTableModel().addRow(rowData);
			}

		}

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void initInfo() {
		// 读取配置文件数据库配置
		String user = this.getUserField().getText();
		String pass = this.getPwdField().getText();
		String ip = this.getIpFiled().getText();
		String database = this.getDbNameFiled().getText();
		String dbName = this.getDbBox().getSelectedItem().toString();
		String packName = this.getPackField().getText();
		String catName = this.getCatField().getText();
		// 处理界面数据
		dbInfoMap = new HashMap();
		dbInfoMap = dbMap.get(dbName);
		dbInfoMap.put("userName", user);
		dbInfoMap.put("userpwd", pass);
		dbInfoMap.put("jdbc", dbMap.get(dbName).get("JdbcURL") + ip
				+ dbMap.get(dbName).get("dbStr") + database);
		dbInfoMap.put("driver", dbMap.get(dbName).get("driverClassName"));
		dbInfoMap.put("dbName", database);
		dbInfoMap.put("packName", packName);
		dbInfoMap.put("catName", catName);

		// Edit By HuangJun 2014-07-07
		System.out.println(dbInfoMap);

		/*
		 * MYSQL dataSource.driverClassName=com.mysql.jdbc.Driver
		 * dataSource.url=
		 * jdbc:mysql://192.168.12.55/fzrating?characterEncoding=utf8
		 * dataSource.username= fzratingtest dataSource.password= fzratingtest
		 * {userpwd=fzratingtest, dbName=fzrating, JdbcURL=jdbc:mysql://,
		 * catName=f:\, userName=fzratingtest,
		 * driverClassName=com.mysql.jdbc.Driver, driver=com.mysql.jdbc.Driver,
		 * dbStr=/, showTable=select table_name from information_schema.tables
		 * where table_schema = '%', packName=davaDemo, showColumns=show columns
		 * from %, jdbc=jdbc:mysql://192.168.12.55:3306/fzrating}
		 */

		/*
		 * SQlServer dataSource.driverClassName=net.sourceforge.jtds.jdbc.Driver
		 * dataSource.url=jdbc:jtds:sqlserver://192.168.12.54:1433/FZ_DEV
		 * dataSource.username= fz_dev dataSource.password= fz_dev
		 * {userpwd=fz_dev, dbName=fz_dev, JdbcURL=jdbc:sqlserver://,
		 * catName=f:\, userName=fz_dev,
		 * driverClassName=com.microsoft.sqlserver.jdbc.SQLServerDriver,
		 * driver=com.microsoft.sqlserver.jdbc.SQLServerDriver,
		 * dbStr=;databaseName=, showTable=select Name from sysobjects where
		 * xtype='u' and status>=0, packName=javaDemo, showColumns=select
		 * column_name,data_type from information_schema.columns where
		 * table_name = '%',
		 * jdbc=jdbc:sqlserver://192.168.12.54:1433;databaseName=fz_dev}
		 */

		/*
		 * Oracle
		 * dataSourceforSe.driverClassName=oracle.jdbc.driver.OracleDriver
		 * dataSourceforSe.url=jdbc:oracle:thin:@192.168.12.230:1521:demo230
		 * dataSourceforSe.username=ccxe dataSourceforSe.password=ccxe
		 * {userpwd=ccxe, dbName=demo230, JdbcURL=jdbc:oracle:thin:@,
		 * catName=f:\, userName=ccxe,
		 * driverClassName=oracle.jdbc.driver.OracleDriver,
		 * driver=oracle.jdbc.driver.OracleDriver, dbStr=:, showTable=SELECT
		 * TABLE_NAME FROM USER_TABLES, packName=javaDemo, showColumns=select
		 * table_name from dba_tables where owner='CCXE',
		 * jdbc=jdbc:oracle:thin:@192.168.12.230:1521:demo230}
		 */

	}

	private void close() {
		System.out.println("关闭事件");
	}

	public JTextField getIpFiled() {
		return ipFiled;
	}

	public void setIpFiled(JTextField ipFiled) {
		this.ipFiled = ipFiled;
	}

	public JTextField getDbFiled() {
		return dbFiled;
	}

	public void setDbFiled(JTextField dbFiled) {
		this.dbFiled = dbFiled;
	}

	public JTextField getTabField() {
		return tabField;
	}

	public void setTabField(JTextField tabField) {
		this.tabField = tabField;
	}

	public JTextField getPackField() {
		return packField;
	}

	public void setPackField(JTextField packField) {
		this.packField = packField;
	}

	public JTextField getCatField() {
		return catField;
	}

	public void setCatField(JTextField catField) {
		this.catField = catField;
	}

	public JCheckBox getCheckBox() {
		return checkBox;
	}

	public void setCheckBox(JCheckBox checkBox) {
		this.checkBox = checkBox;
	}

	public JTextField getUserField() {
		return userField;
	}

	public void setUserField(JTextField userField) {
		this.userField = userField;
	}

	public JTextField getPwdField() {
		return pwdField;
	}

	public void setPwdField(JTextField pwdField) {
		this.pwdField = pwdField;
	}

	public JTextField getDbNameFiled() {
		return dbNameFiled;
	}

	public void setDbNameFiled(JTextField dbNameFiled) {
		this.dbNameFiled = dbNameFiled;
	}

	public JComboBox getDbBox() {
		return dbBox;
	}

	public void setDbBox(JComboBox dbBox) {
		this.dbBox = dbBox;
	}

	public JLabel getLabelInfo() {
		return labelInfo;
	}

	public void setLabelInfo(JLabel labelInfo) {
		this.labelInfo = labelInfo;
	}

	public JTable getJtable() {
		return jtable;
	}

	public void setJtable(JTable jtable) {
		this.jtable = jtable;
	}

	public MyTableModel getTableModel() {
		return tableModel;
	}

	public void setTableModel(MyTableModel tableModel) {
		this.tableModel = tableModel;
	}

	@SuppressWarnings("serial")
	class MyTableModel extends DefaultTableModel {
		public MyTableModel(Object[][] data, String[] columns) {
			super(data, columns);
		}

		public boolean isCellEditable(int row, int column) { // 设置Table单元格是否可编辑
			if (column == 0)
				return true;
			return false;
		}

		public Class<?> getColumnClass(int columnIndex) {
			if (columnIndex == 0) {
				return Boolean.class;
			}
			return Object.class;
		}

	}
}
