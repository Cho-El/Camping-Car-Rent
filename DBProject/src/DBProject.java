import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class DBProject extends JFrame implements ActionListener {
	static Connection con;
	int userRentState;
	Statement stmt;
	PreparedStatement pstmt;
	ResultSet rs;
	String Driver = "com.mysql.cj.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/madang?&serverTimezone=Asia/Seoul&useSSL=false";
	String userid = "madang";
	String pwd = "madang";
	String state, mode;
	JPanel header, initPanel, userPanel, managerHead, managerMenu, customerPn, customerInsertPn, companyPn,
			companyInsertPn;
	JPanel userHead, userMenu, userCarPn, managerPanel;
	JButton userMod, manageMod, btnCustomer, btnCompany, btnCampingcar, btnRepairshop, btnCustomerInsert,
			btnCustomerEdit, btnCustomerDelete, btnBack, btnInit;
	JButton btnSearchCar, btnCustomerInsertSave, btnUserRent, btnUserLogin, btnGotoSignup, btnUserSearch,
			btnCompanyInsert, btnCompanyEdit, btnCompanyDelete, btnCompanySave;
	JLabel lblUser, lblManage, lblHead, lblLogin, lblManagerHead, lblCustomer, lblCompany;
	JLabel lblHead_user, lblUserId, lblUserName, lblUserAddr, lblUserNum, lblUserEmail, lblCar, lblUserLogin;
	JLabel lblCompanyId, lblCompanyName, lblCompanyAddr, lblCompanyNum, lblCompanyAgent, lblCompanyAgent_e;
	JTextField txtUserId, txtUserName, txtUserAddr, txtUserNum, txtUserEmail, txtUserSearch;
	JTextField txtCompanyId, txtCompanyName, txtCompanyAddr, txtCompanyNum, txtCompanyAgent, txtCompanyAgent_e;

	JPanel carInsertPn;
	JLabel lblCarName, lblCarNum, lblCarMax, lblCarMade, lblCarYear, lblCarDistance, lblCarPrice, lblCarDate,
			lblCarPossible, lblCarCompany;
	JTextField txtCarName, txtCarNum, txtCarMax, txtCarMade, txtCarYear, txtCarDistance, txtCarPrice, txtCarDate,
			txtCarPossible, txtCarCompany;
	JButton btnCarSave;

	JPanel userRentPn;
	JLabel lblClientID, lblRentStart, lblRentPeriod, lblRent, lblUserSearch;
	JTextField txtClientID, txtRentStart, txtRentPeriod;
	JButton btnRentSave, btnUserBack;
	/* repairshop 삽입에 관한 변수 */
	JPanel repairInsertPn;
	JLabel lblShopId, lblShopName, lblShopAddr, lblShopNum, lblShopAgent, lblShopEmail;
	JTextField txtShopId, txtShopName, txtShopAddr, txtShopNum, txtShopAgent, txtShopEmail;
	JButton btnRepairSave;
	/* rental histodry에 관한 변수 */
	JButton btnRental, btnReturn, btnCheckReturn;
	JLabel lblRental;
	JPanel rentalPn;

	JTable clientTable, companyTable, carTable, repairshopTable, userCarTable, rentTable, repairTable;
	DefaultTableModel customerModel, companyModel, carModel, repairshopModel, userCarModel, rentModel, repairModel;
	JPanel carPn;
	JButton btnCarInsert, btnCarDelete, btnCarEdit;
	JLabel lblRepair;
	JPanel repairPn;
	JButton btnRepairInsert, btnRepairEdit, btnRepairDelete;
	JScrollPane userCarScroll, clientScroll, companyScroll, carScroll, shopScroll, rentScroll;
	/* 정비내역에 관한변수 */
	JPanel repairHistoryPn;
	JLabel lblRepairHistory;
	JTable repairHistoryTable;
	JScrollPane rhScroll;
	DefaultTableModel rhModel;
	JButton btnEdit_RH, btnDelete_RH, btnRepair, btnRequest;
	JPanel rePn;
	JLabel lblContent_repair, lblDate_repair, lblCharge_repair, lblDue_repair, lblOthers_repair;
	JTextField txtContent_repair, txtCharge_repair, txtDue_repair, txtDate_repair, txtOthers_repair;
	JButton btnSave_repair;
	/* 점검내역에 관한 변수 */
	JPanel testPn;
	JLabel lblCarID_test, lblTestFront, lblTestLeft, lblTestRight, lblTestBack, lblRentID_test;
	JLabel lblFixNeed, lblClientID_test, lblCompanyID_test, lblShop_test, lblIfFix;
	JTextField txtCarID_test, txtTestFront, txtTestLeft, txtTestRight, txtTestBack, txtClientID_test, txtCompanyID_test;
	JTextField txtShopID_test, txtRentID_test;
	JButton btnSave_test;
	JCheckBox fixNeed;
	/* 검색 버튼 4개 */
	JButton search, search1, search2, search3, search4;
	JPanel searchPn;
	JTable searchTable;
	JScrollPane searchScroll;
	JLabel lblSearch1, lblSearch2, lblSearch3, lblSearch4;

	public DBProject() {
		super("Swing Database");
		conDB();
		initDB();
		setVisible(true);
		setLayout(null);
		userMode();
		managerMode();
		initLayout();
		initPanel.setVisible(true);
		setBounds(200, 200, 1000, 600); // 가로위치,세로위치,가로길이,세로길이
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void initLayout() {

		initPanel = new JPanel();
		initPanel.setBounds(0, 0, 1000, 600);
		add(initPanel);
		initPanel.setVisible(false);
		initPanel.setLayout(null);

		header = new JPanel();
		header.setBounds(0, 0, 1000, 100);
		header.setBackground(Color.decode("#8198A6"));
		lblHead = new JLabel("16011101 조성윤");
		lblHead.setHorizontalAlignment(JLabel.CENTER);
		lblHead.setFont(new Font("", Font.BOLD, 50));
		header.add(lblHead);
		initPanel.add(header);

		userMod = new JButton();
		userMod.setBounds(0, 100, 500, 900);
		userMod.setBackground(Color.decode("#D9C5C5"));
		userMod.setLayout(null);
		lblUser = new JLabel("USER MODE");
		lblUser.setBounds(100, 0, 500, 500);
		lblUser.setFont(new Font("", Font.BOLD, 50));
		userMod.add(lblUser);

		initPanel.add(userMod);

		manageMod = new JButton();
		manageMod.setBounds(500, 100, 500, 900);
		manageMod.setBackground(Color.decode("#D9C5C5"));
		manageMod.setLayout(null);
		lblManage = new JLabel("MANAGER MODE");
		lblManage.setBounds(24, 0, 500, 500);
		lblManage.setFont(new Font("", Font.BOLD, 50));
		manageMod.add(lblManage);
		initPanel.add(manageMod);

		userMod.addActionListener(this);
		manageMod.addActionListener(this);

	}

	public void managerMode() {
		managerPanel = new JPanel();
		managerPanel.setLayout(null);
		managerPanel.setVisible(false);
		managerPanel.setBounds(0, 0, 1000, 600);
		add(managerPanel);

		managerHead = new JPanel();
		managerHead.setBounds(0, 0, 1000, 100);
		managerHead.setBackground(Color.decode("#8198A6"));
		lblManagerHead = new JLabel("MANAGER MODE");
		lblManagerHead.setHorizontalAlignment(JLabel.CENTER);
		lblManagerHead.setFont(new Font("", Font.BOLD, 50));
		managerHead.add(lblManagerHead);
		managerPanel.add(managerHead);
		managerMenu = new JPanel();
		managerMenu.setBounds(0, 100, 200, 500);
		managerMenu.setBackground(Color.decode("#D9C5C5"));
		managerMenu.setLayout(null);
		managerPanel.add(managerMenu);

		btnCustomer = new JButton("CUSTOMER");
		btnCustomer.setBounds(0, 0, 200, 50);
		btnCustomer.setBackground(Color.decode("#F2F2F2"));
		managerMenu.add(btnCustomer);
		btnCompany = new JButton("COMPANY");
		btnCompany.setBounds(0, 50, 200, 50);
		btnCompany.setBackground(Color.decode("#F2F2F2"));
		managerMenu.add(btnCompany);
		btnCampingcar = new JButton("CAMPING CAR");
		btnCampingcar.setBounds(0, 100, 200, 50);
		btnCampingcar.setBackground(Color.decode("#F2F2F2"));
		managerMenu.add(btnCampingcar);
		btnRepairshop = new JButton("REPAIRSHOP");
		btnRepairshop.setBounds(0, 150, 200, 50);
		btnRepairshop.setBackground(Color.decode("#F2F2F2"));
		managerMenu.add(btnRepairshop);
		btnRental = new JButton("RENTAL MANAGEMENT");
		btnRental.setBounds(0, 200, 200, 50);
		btnRental.setBackground(Color.decode("#F2F2F2"));
		managerMenu.add(btnRental);

		btnRepair = new JButton("REPAIR MANAGEMENT");
		btnRepair.setBounds(0, 250, 200, 50);
		btnRepair.setBackground(Color.decode("#F2F2F2"));
		managerMenu.add(btnRepair);

		search = new JButton("SEARCH");
		search.setBounds(0, 300, 200, 50);
		search.setBackground(Color.decode("#F2F2F2"));
		managerMenu.add(search);

		btnInit = new JButton("INIT");
		btnInit.setBounds(0, 365, 200, 50);
		btnInit.setBackground(Color.decode("#F2F2F2"));
		managerMenu.add(btnInit);

		btnBack = new JButton("BACK");
		btnBack.setBounds(0, 415, 200, 50);
		btnBack.setBackground(Color.decode("#F2F2F2"));
		managerMenu.add(btnBack);

		customer();
		customerInsert();
		company();
		companyInsert();
		campingcar();
		campingcarInsert();
		repairShop();
		repairShopInsert();
		rental();
		test();
		repairHistory();
		repairEdit();
		search();

		btnInit.addActionListener(this);
		search.addActionListener(this);
		btnCustomer.addActionListener(this);
		btnCompany.addActionListener(this);
		btnCampingcar.addActionListener(this);
		btnRental.addActionListener(this);
		btnRepairshop.addActionListener(this);
		btnBack.addActionListener(this);
		btnRepair.addActionListener(this);
	}

	public void userMode() {
		userPanel = new JPanel();
		userPanel.setLayout(null);
		userPanel.setBounds(0, 0, 1000, 600);
		userPanel.setVisible(false);
		add(userPanel);

		userHead = new JPanel();
		userHead.setBounds(0, 0, 1000, 100);
		userHead.setBackground(Color.decode("#8198A6"));
		lblHead_user = new JLabel("USER MODE");
		lblHead_user.setHorizontalAlignment(JLabel.CENTER);
		lblHead_user.setFont(new Font("", Font.BOLD, 50));
		userHead.add(lblHead_user);
		userPanel.add(userHead);

		userMenu = new JPanel();
		userMenu.setBounds(0, 100, 200, 900);
		userMenu.setBackground(Color.decode("#D9C5C5"));
		userMenu.setLayout(null);
		userPanel.add(userMenu);

		btnSearchCar = new JButton("SEARCH CAMPINGCAR");
		btnSearchCar.setBounds(0, 0, 200, 50);
		btnSearchCar.setBackground(Color.decode("#F2F2F2"));
		userMenu.add(btnSearchCar);

		btnUserBack = new JButton("BACK");
		btnUserBack.setBounds(0, 415, 200, 50);
		btnUserBack.setBackground(Color.decode("#F2F2F2"));
		userMenu.add(btnUserBack);

		searchCar();
		userRent();
		btnUserBack.addActionListener(this);
		btnSearchCar.addActionListener(this);
	}

	public void searchCar() {
		userCarPn = new JPanel();
		userCarPn.setBounds(200, 100, 800, 500);
		userCarPn.setBackground(Color.decode("#F2DAD8"));
		userCarPn.setLayout(null);
		userCarPn.setVisible(true);
		userPanel.add(userCarPn);

		lblUserSearch = new JLabel("캠핑카 이름");
		lblUserSearch.setBounds(100, 50, 100, 30);
		userCarPn.add(lblUserSearch);

		txtUserSearch = new JTextField(10);
		txtUserSearch.setBounds(200, 50, 200, 30);
		userCarPn.add(txtUserSearch);

		btnUserSearch = new JButton("SEARCH");
		btnUserSearch.setBounds(450, 50, 100, 30);
		btnUserSearch.setBackground(Color.decode("#F2F2F2"));
		userCarPn.add(btnUserSearch);

		userCarTable = new JTable();
		userCarScroll = new JScrollPane(userCarTable);
		userCarScroll.setBounds(100, 100, 600, 250);
		userCarPn.add(userCarScroll);

		showCampingcar(1, "");

		btnUserRent = new JButton("RENT");
		btnUserRent.setBounds(350, 400, 100, 30);
		btnUserRent.setBackground(Color.decode("#F2F2F2"));
		userCarPn.add(btnUserRent);

		btnUserSearch.addActionListener(this);
		btnUserRent.addActionListener(this);
	}

	public void userRent() {
		userRentPn = new JPanel();
		userRentPn.setBounds(200, 100, 800, 500);
		userRentPn.setBackground(Color.decode("#F2DAD8"));
		userRentPn.setLayout(null);
		userPanel.add(userRentPn);

		lblRent = new JLabel("대여 정보 입력하기");
		lblRent.setBounds(100, 50, 300, 50);
		lblRent.setFont(new Font("", Font.BOLD, 30));
		userRentPn.add(lblRent);

		lblClientID = new JLabel("운전면허증번호");
		lblClientID.setBounds(100, 150, 200, 30);
		lblClientID.setFont(new Font("", Font.PLAIN, 20));
		userRentPn.add(lblClientID);

		lblRentStart = new JLabel("대여시작일");
		lblRentStart.setBounds(100, 200, 200, 30);
		lblRentStart.setFont(new Font("", Font.PLAIN, 20));
		userRentPn.add(lblRentStart);

		lblRentPeriod = new JLabel("대여기간(일)");
		lblRentPeriod.setBounds(100, 250, 200, 30);
		lblRentPeriod.setFont(new Font("", Font.PLAIN, 20));
		userRentPn.add(lblRentPeriod);

		txtClientID = new JTextField(10);
		txtClientID.setBounds(300, 150, 200, 30);
		userRentPn.add(txtClientID);

		txtRentStart = new JTextField(10);
		txtRentStart.setBounds(300, 200, 200, 30);
		userRentPn.add(txtRentStart);

		txtRentPeriod = new JTextField(30);
		txtRentPeriod.setBounds(300, 250, 200, 30);
		userRentPn.add(txtRentPeriod);

		btnRentSave = new JButton("RENT");
		btnRentSave.setBounds(250, 350, 100, 30);
		btnRentSave.setBackground(Color.decode("#F2F2F2"));
		userRentPn.add(btnRentSave);

		userRentPn.setVisible(false);
		btnRentSave.addActionListener(this);
	}

	public void search() {
		searchPn = new JPanel();
		searchPn.setBounds(200, 100, 800, 500);
		searchPn.setBackground(Color.decode("#F2DAD8"));
		searchPn.setLayout(null);
		searchPn.setVisible(false);
		managerPanel.add(searchPn);

		search1 = new JButton("SEARCH 1");
		search1.setBounds(120, 50, 100, 30);
		search1.setBackground(Color.decode("#F2F2F2"));
		searchPn.add(search1);

		search2 = new JButton("SEARCH 2");
		search2.setBounds(270, 50, 100, 30);
		search2.setBackground(Color.decode("#F2F2F2"));
		searchPn.add(search2);

		search3 = new JButton("SEARCH 3");
		search3.setBounds(420, 50, 100, 30);
		search3.setBackground(Color.decode("#F2F2F2"));
		searchPn.add(search3);

		search4 = new JButton("SEARCH 4");
		search4.setBounds(570, 50, 100, 30);
		search4.setBackground(Color.decode("#F2F2F2"));
		searchPn.add(search4);

		searchTable = new JTable();
		searchScroll = new JScrollPane(searchTable);
		searchScroll.setBounds(100, 100, 600, 250);
		searchPn.add(searchScroll);

		lblSearch1 = new JLabel("가격이 7만원 이하이면서 대여 횟수가 2회 이상인 캠핑카에 대한 정보");
		lblSearch1.setBounds(100, 370, 700, 30);
		lblSearch1.setFont(new Font("", Font.ITALIC, 15));
		lblSearch1.setVisible(false);
		searchPn.add(lblSearch1);

		lblSearch2 = new JLabel("대여가 가능한 캠핑카들 중의 반환시 수리여부가 1건 이하인 캠핑카에 대한 정보");
		lblSearch2.setBounds(100, 370, 700, 30);
		lblSearch2.setFont(new Font("", Font.ITALIC, 15));
		lblSearch2.setVisible(false);
		searchPn.add(lblSearch2);

		lblSearch3 = new JLabel("승차인원수가 5명 이상이면서, 지금까지의 수리 비용의 합계가 510000원 이하로 들어갔었 던 캠핑카의 회사에 대한 정보");
		lblSearch3.setBounds(100, 370, 700, 30);
		lblSearch3.setFont(new Font("", Font.ITALIC, 13));
		lblSearch3.setVisible(false);
		searchPn.add(lblSearch3);

		lblSearch4 = new JLabel("수리비용이 200000원 이상이 들어갔었던 캠핑카정비소의 관한 정보");
		lblSearch4.setBounds(100, 370, 700, 30);
		lblSearch4.setFont(new Font("", Font.ITALIC, 15));
		lblSearch4.setVisible(false);
		searchPn.add(lblSearch4);

		search1.addActionListener(this);
		search2.addActionListener(this);
		search3.addActionListener(this);
		search4.addActionListener(this);
	}

	public void rental() {

		rentalPn = new JPanel();
		rentalPn.setBounds(200, 100, 800, 500);
		rentalPn.setBackground(Color.decode("#F2DAD8"));
		rentalPn.setLayout(null);
		rentalPn.setVisible(false);
		managerPanel.add(rentalPn);

		lblRental = new JLabel("Rental History");
		lblRental.setBounds(270, 35, 600, 50);
		lblRental.setFont(new Font("", Font.BOLD, 30));
		rentalPn.add(lblRental);

		rentTable = new JTable();
		rentScroll = new JScrollPane(rentTable);
		rentScroll.setBounds(100, 100, 600, 250);
		rentalPn.add(rentScroll);

		showRentalHistory("");

		btnCheckReturn = new JButton("Check Return");
		btnCheckReturn.setBounds(580, 50, 120, 30);
		btnCheckReturn.setBackground(Color.decode("#F2F2F2"));
		rentalPn.add(btnCheckReturn);

		btnReturn = new JButton("RETURN");
		btnReturn.setBounds(330, 400, 100, 30);
		btnReturn.setBackground(Color.decode("#F2F2F2"));
		rentalPn.add(btnReturn);

		btnCheckReturn.addActionListener(this);
		btnReturn.addActionListener(this);

	}

	public void repairEdit() {

		rePn = new JPanel();
		rePn.setBounds(200, 100, 800, 500);
		rePn.setBackground(Color.decode("#F2DAD8"));
		rePn.setLayout(null);
		rePn.setVisible(false);
		managerPanel.add(rePn);

		lblContent_repair = new JLabel("정비 내역");
		lblContent_repair.setBounds(100, 100, 200, 30);
		lblContent_repair.setFont(new Font("", Font.PLAIN, 20));
		rePn.add(lblContent_repair);

		lblDate_repair = new JLabel("정비 날짜");
		lblDate_repair.setBounds(100, 150, 200, 30);
		lblDate_repair.setFont(new Font("", Font.PLAIN, 20));
		rePn.add(lblDate_repair);

		lblCharge_repair = new JLabel("청구 요금");
		lblCharge_repair.setBounds(100, 200, 200, 30);
		lblCharge_repair.setFont(new Font("", Font.PLAIN, 20));
		rePn.add(lblCharge_repair);

		lblDue_repair = new JLabel("납입 기한");
		lblDue_repair.setBounds(100, 250, 200, 30);
		lblDue_repair.setFont(new Font("", Font.PLAIN, 20));
		rePn.add(lblDue_repair);

		lblOthers_repair = new JLabel("기타 수리 내역");
		lblOthers_repair.setBounds(100, 300, 200, 30);
		lblOthers_repair.setFont(new Font("", Font.PLAIN, 20));
		rePn.add(lblOthers_repair);

		txtContent_repair = new JTextField(30);
		txtContent_repair.setBounds(300, 100, 200, 30);
		rePn.add(txtContent_repair);

		txtDate_repair = new JTextField(10);
		txtDate_repair.setBounds(300, 150, 200, 30);
		rePn.add(txtDate_repair);

		txtCharge_repair = new JTextField(10);
		txtCharge_repair.setBounds(300, 200, 200, 30);
		rePn.add(txtCharge_repair);

		txtDue_repair = new JTextField(10);
		txtDue_repair.setBounds(300, 250, 200, 30);
		rePn.add(txtDue_repair);

		txtOthers_repair = new JTextField(30);
		txtOthers_repair.setBounds(300, 300, 200, 30);
		rePn.add(txtOthers_repair);

		btnSave_repair = new JButton("SAVE");
		btnSave_repair.setBounds(200, 370, 200, 30);
		btnSave_repair.setBackground(Color.decode("#F2F2F2"));
		rePn.add(btnSave_repair);

		btnSave_repair.addActionListener(this);
	}

	public void repairHistory() {

		repairHistoryPn = new JPanel();
		repairHistoryPn.setBounds(200, 100, 800, 500);
		repairHistoryPn.setBackground(Color.decode("#F2DAD8"));
		repairHistoryPn.setLayout(null);
		repairHistoryPn.setVisible(false);
		managerPanel.add(repairHistoryPn);

		lblRepairHistory = new JLabel("Repair History");
		lblRepairHistory.setBounds(270, 35, 400, 50);
		lblRepairHistory.setFont(new Font("", Font.BOLD, 30));
		repairHistoryPn.add(lblRepairHistory);

		repairHistoryTable = new JTable();
		rhScroll = new JScrollPane(repairHistoryTable);
		rhScroll.setBounds(100, 100, 600, 250);
		repairHistoryPn.add(rhScroll);

		showRepairHistory("");

		btnRequest = new JButton("Request");
		btnRequest.setBounds(600, 50, 100, 30);
		btnRequest.setBackground(Color.decode("#F2F2F2"));
		repairHistoryPn.add(btnRequest);

		btnEdit_RH = new JButton("EDIT");
		btnEdit_RH.setBounds(200, 400, 100, 30);
		btnEdit_RH.setBackground(Color.decode("#F2F2F2"));
		repairHistoryPn.add(btnEdit_RH);

		btnDelete_RH = new JButton("DELETE");
		btnDelete_RH.setBounds(500, 400, 100, 30);
		btnDelete_RH.setBackground(Color.decode("#F2F2F2"));
		repairHistoryPn.add(btnDelete_RH);

		btnRequest.addActionListener(this);
		btnEdit_RH.addActionListener(this);
		btnDelete_RH.addActionListener(this);
	}

	public void test() {

		testPn = new JPanel();
		testPn.setBounds(200, 100, 800, 500);
		testPn.setBackground(Color.decode("#F2DAD8"));
		testPn.setLayout(null);
		testPn.setVisible(false);
		managerPanel.add(testPn);

		lblRentID_test = new JLabel("대여번호");
		lblRentID_test.setBounds(50, 50, 200, 30);
		lblRentID_test.setFont(new Font("", Font.PLAIN, 20));
		testPn.add(lblRentID_test);

		lblCarID_test = new JLabel("캠핑카ID");
		lblCarID_test.setBounds(420, 50, 200, 30);
		lblCarID_test.setFont(new Font("", Font.PLAIN, 20));
		testPn.add(lblCarID_test);

		lblCompanyID_test = new JLabel("대여회사ID");
		lblCompanyID_test.setBounds(420, 100, 200, 30);
		lblCompanyID_test.setFont(new Font("", Font.PLAIN, 20));
		testPn.add(lblCompanyID_test);

		lblClientID_test = new JLabel("운전면허증");
		lblClientID_test.setBounds(420, 150, 200, 30);
		lblClientID_test.setFont(new Font("", Font.PLAIN, 20));
		testPn.add(lblClientID_test);

		lblTestFront = new JLabel("앞 설명");
		lblTestFront.setBounds(50, 100, 200, 30);
		lblTestFront.setFont(new Font("", Font.PLAIN, 20));
		testPn.add(lblTestFront);

		lblTestLeft = new JLabel("왼쪽 설명");
		lblTestLeft.setBounds(50, 150, 200, 30);
		lblTestLeft.setFont(new Font("", Font.PLAIN, 20));
		testPn.add(lblTestLeft);

		lblTestRight = new JLabel("오른쪽 설명");
		lblTestRight.setBounds(50, 200, 200, 30);
		lblTestRight.setFont(new Font("", Font.PLAIN, 20));
		testPn.add(lblTestRight);

		lblTestBack = new JLabel("뒤 설명");
		lblTestBack.setBounds(50, 250, 200, 30);
		lblTestBack.setFont(new Font("", Font.PLAIN, 20));
		testPn.add(lblTestBack);

		lblFixNeed = new JLabel("수리 필요");
		lblFixNeed.setBounds(50, 300, 200, 30);
		lblFixNeed.setFont(new Font("", Font.PLAIN, 20));
		testPn.add(lblFixNeed);

		lblIfFix = new JLabel("수리 필요 시, 체크 후 원하는 정비소 아이디를 입력하십시오.");
		lblIfFix.setBounds(50, 330, 300, 20);
		lblIfFix.setFont(new Font("", Font.ITALIC, 10));
		testPn.add(lblIfFix);

		txtRentID_test = new JTextField(10);
		txtRentID_test.setEditable(false);
		txtRentID_test.setBounds(200, 50, 200, 30);
		testPn.add(txtRentID_test);

		txtCarID_test = new JTextField(10);
		txtCarID_test.setEditable(false);
		txtCarID_test.setBounds(550, 50, 200, 30);
		testPn.add(txtCarID_test);

		txtCompanyID_test = new JTextField(10);
		txtCompanyID_test.setBounds(550, 100, 200, 30);
		txtCompanyID_test.setEditable(false);
		testPn.add(txtCompanyID_test);

		txtClientID_test = new JTextField(20);
		txtClientID_test.setBounds(550, 150, 200, 30);
		txtClientID_test.setEditable(false);
		testPn.add(txtClientID_test);

		txtTestFront = new JTextField(20);
		txtTestFront.setBounds(200, 100, 200, 30);
		testPn.add(txtTestFront);

		txtTestLeft = new JTextField(20);
		txtTestLeft.setBounds(200, 150, 200, 30);
		testPn.add(txtTestLeft);

		txtTestRight = new JTextField(20);
		txtTestRight.setBounds(200, 200, 200, 30);
		testPn.add(txtTestRight);

		txtTestBack = new JTextField(20);
		txtTestBack.setBounds(200, 250, 200, 30);
		testPn.add(txtTestBack);

		fixNeed = new JCheckBox();
		fixNeed.setBounds(150, 300, 30, 30);
		fixNeed.setBackground(Color.decode("#F2DAD8"));
		testPn.add(fixNeed);

		txtShopID_test = new JTextField(10);
		txtShopID_test.setBounds(200, 300, 200, 30);
		txtShopID_test.setEditable(false);
		testPn.add(txtShopID_test);

		btnSave_test = new JButton("SAVE");
		btnSave_test.setBounds(300, 370, 100, 30);
		btnSave_test.setBackground(Color.decode("#F2F2F2"));
		testPn.add(btnSave_test);

		fixNeed.addActionListener(this);
		btnSave_test.addActionListener(this);

	}

	public void repairShopInsert() {
		repairInsertPn = new JPanel();
		repairInsertPn.setBounds(200, 100, 800, 500);
		repairInsertPn.setBackground(Color.decode("#F2DAD8"));
		repairInsertPn.setLayout(null);
		repairInsertPn.setVisible(false);
		managerPanel.add(repairInsertPn);

		lblShopName = new JLabel("정비소 이름");
		lblShopName.setBounds(100, 100, 200, 30);
		lblShopName.setFont(new Font("", Font.PLAIN, 20));
		repairInsertPn.add(lblShopName);

		lblShopAddr = new JLabel("정비소 주소");
		lblShopAddr.setBounds(100, 150, 200, 30);
		lblShopAddr.setFont(new Font("", Font.PLAIN, 20));
		repairInsertPn.add(lblShopAddr);

		lblShopNum = new JLabel("정비소 번호");
		lblShopNum.setBounds(100, 200, 200, 30);
		lblShopNum.setFont(new Font("", Font.PLAIN, 20));
		repairInsertPn.add(lblShopNum);

		lblShopAgent = new JLabel("담당자 이름");
		lblShopAgent.setBounds(100, 250, 200, 30);
		lblShopAgent.setFont(new Font("", Font.PLAIN, 20));
		repairInsertPn.add(lblShopAgent);

		lblShopEmail = new JLabel("담당자 이메일");
		lblShopEmail.setBounds(100, 300, 200, 30);
		lblShopEmail.setFont(new Font("", Font.PLAIN, 20));
		repairInsertPn.add(lblShopEmail);

		txtShopName = new JTextField(10);
		txtShopName.setBounds(300, 100, 200, 30);
		repairInsertPn.add(txtShopName);

		txtShopAddr = new JTextField(20);
		txtShopAddr.setBounds(300, 150, 200, 30);
		repairInsertPn.add(txtShopAddr);

		txtShopNum = new JTextField(15);
		txtShopNum.setBounds(300, 200, 200, 30);
		repairInsertPn.add(txtShopNum);

		txtShopAgent = new JTextField(10);
		txtShopAgent.setBounds(300, 250, 200, 30);
		repairInsertPn.add(txtShopAgent);

		txtShopEmail = new JTextField(20);
		txtShopEmail.setBounds(300, 300, 200, 30);
		repairInsertPn.add(txtShopEmail);

		btnRepairSave = new JButton("SAVE");
		btnRepairSave.setBounds(200, 370, 200, 30);
		btnRepairSave.setBackground(Color.decode("#F2F2F2"));
		repairInsertPn.add(btnRepairSave);

		btnRepairSave.addActionListener(this);
	}

	public void repairShop() {
		repairPn = new JPanel();
		repairPn.setBounds(200, 100, 800, 500);
		repairPn.setBackground(Color.decode("#F2DAD8"));
		repairPn.setLayout(null);
		managerPanel.add(repairPn);

		lblRepair = new JLabel("RepairShop information management");
		lblRepair.setBounds(130, 35, 600, 50);
		lblRepair.setFont(new Font("", Font.BOLD, 30));
		repairPn.add(lblRepair);

		repairshopTable = new JTable();
		shopScroll = new JScrollPane(repairshopTable);
		shopScroll.setBounds(100, 100, 600, 250);
		repairPn.add(shopScroll);

		showRepairshop("");

		btnRepairInsert = new JButton("INSERT");
		btnRepairInsert.setBounds(130, 400, 100, 30);
		btnRepairInsert.setBackground(Color.decode("#F2F2F2"));
		repairPn.add(btnRepairInsert);

		btnRepairEdit = new JButton("EDIT");
		btnRepairEdit.setBounds(330, 400, 100, 30);
		btnRepairEdit.setBackground(Color.decode("#F2F2F2"));
		repairPn.add(btnRepairEdit);

		btnRepairDelete = new JButton("DELETE");
		btnRepairDelete.setBounds(530, 400, 100, 30);
		btnRepairDelete.setBackground(Color.decode("#F2F2F2"));
		repairPn.add(btnRepairDelete);

		repairPn.setVisible(false);

		btnRepairInsert.addActionListener(this);
		btnRepairEdit.addActionListener(this);
		btnRepairDelete.addActionListener(this);
	}

	/* -----------------Company테이블 삽입/변경을 위 보여주기 패널------------------------ */
	public void campingcarInsert() {
		carInsertPn = new JPanel();
		carInsertPn.setBounds(200, 100, 800, 500);
		carInsertPn.setBackground(Color.decode("#F2DAD8"));
		carInsertPn.setLayout(null);
		carInsertPn.setVisible(false);
		managerPanel.add(carInsertPn);

		lblCarName = new JLabel("캠핑카 이름");
		lblCarName.setBounds(10, 50, 100, 30);
		lblCarName.setFont(new Font("", Font.PLAIN, 15));
		carInsertPn.add(lblCarName);
		lblCarNum = new JLabel("차량번호");
		lblCarNum.setFont(new Font("", Font.PLAIN, 15));
		lblCarNum.setBounds(10, 100, 100, 30);
		carInsertPn.add(lblCarNum);
		lblCarMax = new JLabel(" 최대승차인원");
		lblCarMax.setBounds(10, 150, 100, 30);
		lblCarMax.setFont(new Font("", Font.PLAIN, 15));
		carInsertPn.add(lblCarMax);
		lblCarMade = new JLabel("제조회사");
		lblCarMade.setBounds(10, 200, 100, 30);
		lblCarMade.setFont(new Font("", Font.PLAIN, 15));
		carInsertPn.add(lblCarMade);
		lblCarYear = new JLabel("제조년도");
		lblCarYear.setBounds(10, 250, 100, 30);
		lblCarYear.setFont(new Font("", Font.PLAIN, 15));
		carInsertPn.add(lblCarYear);
		lblCarDistance = new JLabel("누적주행거리");
		lblCarDistance.setBounds(410, 50, 100, 30);
		lblCarDistance.setFont(new Font("", Font.PLAIN, 15));
		carInsertPn.add(lblCarDistance);
		lblCarPrice = new JLabel("가격");
		lblCarPrice.setBounds(410, 100, 100, 30);
		lblCarPrice.setFont(new Font("", Font.PLAIN, 15));
		carInsertPn.add(lblCarPrice);
		lblCarDate = new JLabel("등록일자");
		lblCarDate.setBounds(410, 150, 100, 30);
		lblCarDate.setFont(new Font("", Font.PLAIN, 15));
		carInsertPn.add(lblCarDate);
		lblCarPossible = new JLabel("캠핑카 사용가능 유무");
		lblCarPossible.setBounds(410, 200, 100, 30);
		lblCarPossible.setFont(new Font("", Font.PLAIN, 15));
		carInsertPn.add(lblCarPossible);
		lblCarCompany = new JLabel("대여회사ID");
		lblCarCompany.setFont(new Font("", Font.PLAIN, 15));
		lblCarCompany.setBounds(410, 250, 100, 30);
		carInsertPn.add(lblCarCompany);

		txtCarName = new JTextField(10);
		txtCarName.setBounds(130, 50, 200, 30);
		carInsertPn.add(txtCarName);
		txtCarNum = new JTextField(10);
		txtCarNum.setBounds(130, 100, 200, 30);
		carInsertPn.add(txtCarNum);
		txtCarMax = new JTextField(10);
		txtCarMax.setBounds(130, 150, 200, 30);
		carInsertPn.add(txtCarMax);
		txtCarMade = new JTextField(10);
		txtCarMade.setBounds(130, 200, 200, 30);
		carInsertPn.add(txtCarMade);
		txtCarYear = new JTextField(10);
		txtCarYear.setBounds(130, 250, 200, 30);
		carInsertPn.add(txtCarYear);
		txtCarDistance = new JTextField(10);
		txtCarDistance.setBounds(530, 50, 200, 30);
		carInsertPn.add(txtCarDistance);
		txtCarPrice = new JTextField(10);
		txtCarPrice.setBounds(530, 100, 200, 30);
		carInsertPn.add(txtCarPrice);

		txtCarDate = new JTextField(10);
		txtCarDate.setEditable(false);
		txtCarDate.setBounds(530, 150, 200, 30);
		carInsertPn.add(txtCarDate);
		txtCarPossible = new JTextField(2);
		txtCarPossible.setBounds(530, 200, 200, 30);
		txtCarPossible.setEditable(false);
		carInsertPn.add(txtCarPossible);
		txtCarCompany = new JTextField(10);
		txtCarCompany.setBounds(530, 250, 200, 30);
		carInsertPn.add(txtCarCompany);

		btnCarSave = new JButton("SAVE");
		btnCarSave.setBounds(500, 300, 100, 30);
		btnCarSave.setBackground(Color.decode("#F2F2F2"));
		carInsertPn.add(btnCarSave);

		btnCarSave.addActionListener(this);
	}

	/* -----------------Campingcar테이블 보여주기 패널------------------------ */
	public void campingcar() {
		carPn = new JPanel();
		carPn.setBounds(200, 100, 800, 500);
		carPn.setBackground(Color.decode("#F2DAD8"));
		carPn.setLayout(null);
		managerPanel.add(carPn);

		lblCar = new JLabel("Campingcar information management");
		lblCar.setBounds(130, 35, 600, 50);
		lblCar.setFont(new Font("", Font.BOLD, 30));
		carPn.add(lblCar);

		carTable = new JTable();
		carScroll = new JScrollPane(carTable);
		carScroll.setBounds(100, 100, 600, 250);
		carPn.add(carScroll);

		showCampingcar(0, "");

		btnCarInsert = new JButton("INSERT");
		btnCarInsert.setBounds(130, 400, 100, 30);
		btnCarInsert.setBackground(Color.decode("#F2F2F2"));
		carPn.add(btnCarInsert);

		btnCarEdit = new JButton("EDIT");
		btnCarEdit.setBounds(330, 400, 100, 30);
		btnCarEdit.setBackground(Color.decode("#F2F2F2"));
		carPn.add(btnCarEdit);

		btnCarDelete = new JButton("DELETE");
		btnCarDelete.setBounds(530, 400, 100, 30);
		btnCarDelete.setBackground(Color.decode("#F2F2F2"));
		carPn.add(btnCarDelete);

		carPn.setVisible(false);

		btnCarInsert.addActionListener(this);
		btnCarEdit.addActionListener(this);
		btnCarDelete.addActionListener(this);
	}


	/* -----------------Company테이블 삽입/변경을 위한 패널------------------------ */
	public void companyInsert() {
		companyInsertPn = new JPanel();
		companyInsertPn.setBounds(200, 100, 800, 500);
		companyInsertPn.setBackground(Color.decode("#F2DAD8"));
		companyInsertPn.setLayout(null);
		managerPanel.add(companyInsertPn);

		lblCompanyName = new JLabel("Company Name");
		lblCompanyName.setBounds(100, 100, 200, 30);
		lblCompanyName.setFont(new Font("", Font.PLAIN, 20));
		companyInsertPn.add(lblCompanyName);

		lblCompanyAddr = new JLabel("Company Address");
		lblCompanyAddr.setBounds(100, 150, 200, 30);
		lblCompanyAddr.setFont(new Font("", Font.PLAIN, 20));
		companyInsertPn.add(lblCompanyAddr);

		lblCompanyNum = new JLabel("Company Number");
		lblCompanyNum.setBounds(100, 200, 200, 30);
		lblCompanyNum.setFont(new Font("", Font.PLAIN, 20));
		companyInsertPn.add(lblCompanyNum);

		lblCompanyAgent = new JLabel("Agent Name");
		lblCompanyAgent.setBounds(100, 250, 200, 30);
		lblCompanyAgent.setFont(new Font("", Font.PLAIN, 20));
		companyInsertPn.add(lblCompanyAgent);

		lblCompanyAgent_e = new JLabel("Agent Email");
		lblCompanyAgent_e.setBounds(100, 300, 200, 30);
		lblCompanyAgent_e.setFont(new Font("", Font.PLAIN, 20));
		companyInsertPn.add(lblCompanyAgent_e);

		txtCompanyName = new JTextField(10);
		txtCompanyName.setBounds(300, 100, 200, 30);
		companyInsertPn.add(txtCompanyName);

		txtCompanyAddr = new JTextField(30);
		txtCompanyAddr.setBounds(300, 150, 200, 30);
		companyInsertPn.add(txtCompanyAddr);

		txtCompanyNum = new JTextField(15);
		txtCompanyNum.setBounds(300, 200, 200, 30);
		companyInsertPn.add(txtCompanyNum);

		txtCompanyAgent = new JTextField(30);
		txtCompanyAgent.setBounds(300, 250, 200, 30);
		companyInsertPn.add(txtCompanyAgent);

		txtCompanyAgent_e = new JTextField(30);
		txtCompanyAgent_e.setBounds(300, 300, 200, 30);
		companyInsertPn.add(txtCompanyAgent_e);

		btnCompanySave = new JButton("SAVE");
		btnCompanySave.setBounds(200, 380, 200, 30);
		btnCompanySave.setBackground(Color.decode("#F2F2F2"));
		companyInsertPn.add(btnCompanySave);

		companyInsertPn.setVisible(false);
		btnCompanySave.addActionListener(this);
	}

	/* -----------------Company테이블 보여주기 패널------------------------ */
	public void company() {
		companyPn = new JPanel();
		companyPn.setBounds(200, 100, 800, 500);
		companyPn.setBackground(Color.decode("#F2DAD8"));
		companyPn.setLayout(null);
		managerPanel.add(companyPn);

		lblCompany = new JLabel("Company information management");
		lblCompany.setBounds(130, 35, 600, 50);
		lblCompany.setFont(new Font("", Font.BOLD, 30));
		companyPn.add(lblCompany);

		companyTable = new JTable();
		companyScroll = new JScrollPane(companyTable);
		companyScroll.setBounds(100, 100, 600, 250);
		companyPn.add(companyScroll);

		showCompany("");

		btnCompanyInsert = new JButton("INSERT");
		btnCompanyInsert.setBounds(130, 400, 100, 30);
		btnCompanyInsert.setBackground(Color.decode("#F2F2F2"));
		companyPn.add(btnCompanyInsert);

		btnCompanyEdit = new JButton("EDIT");
		btnCompanyEdit.setBounds(330, 400, 100, 30);
		btnCompanyEdit.setBackground(Color.decode("#F2F2F2"));
		companyPn.add(btnCompanyEdit);

		btnCompanyDelete = new JButton("DELETE");
		btnCompanyDelete.setBounds(530, 400, 100, 30);
		btnCompanyDelete.setBackground(Color.decode("#F2F2F2"));
		companyPn.add(btnCompanyDelete);

		companyPn.setVisible(false);

		btnCompanyInsert.addActionListener(this);
		btnCompanyEdit.addActionListener(this);
		btnCompanyDelete.addActionListener(this);
	}

	/* -----------------Client테이블 삽입/변경 을 위한 패널------------------------ */
	public void customerInsert() {
		customerInsertPn = new JPanel();
		customerInsertPn.setBounds(200, 100, 800, 500);
		customerInsertPn.setBackground(Color.decode("#F2DAD8"));
		customerInsertPn.setLayout(null);
		managerPanel.add(customerInsertPn);

		lblUserId = new JLabel("운전면허증번호");
		lblUserId.setBounds(100, 50, 200, 30);
		lblUserId.setFont(new Font("", Font.PLAIN, 20));
		customerInsertPn.add(lblUserId);

		lblUserName = new JLabel("고객명");
		lblUserName.setBounds(100, 100, 200, 30);
		lblUserName.setFont(new Font("", Font.PLAIN, 20));
		customerInsertPn.add(lblUserName);

		lblUserAddr = new JLabel("고객주소");
		lblUserAddr.setBounds(100, 150, 200, 30);
		lblUserAddr.setFont(new Font("", Font.PLAIN, 20));
		customerInsertPn.add(lblUserAddr);

		lblUserNum = new JLabel("고객전화번호");
		lblUserNum.setBounds(100, 200, 200, 30);
		lblUserNum.setFont(new Font("", Font.PLAIN, 20));
		customerInsertPn.add(lblUserNum);

		lblUserEmail = new JLabel("고객이메일");
		lblUserEmail.setBounds(100, 250, 200, 30);
		lblUserEmail.setFont(new Font("", Font.PLAIN, 20));
		customerInsertPn.add(lblUserEmail);

		txtUserId = new JTextField(10);
		txtUserId.setBounds(300, 50, 200, 30);
		customerInsertPn.add(txtUserId);

		txtUserName = new JTextField(10);
		txtUserName.setBounds(300, 100, 200, 30);
		customerInsertPn.add(txtUserName);

		txtUserAddr = new JTextField(30);
		txtUserAddr.setBounds(300, 150, 200, 30);
		customerInsertPn.add(txtUserAddr);

		txtUserNum = new JTextField(15);
		txtUserNum.setBounds(300, 200, 200, 30);
		customerInsertPn.add(txtUserNum);

		txtUserEmail = new JTextField(30);
		txtUserEmail.setBounds(300, 250, 200, 30);
		customerInsertPn.add(txtUserEmail);

		btnCustomerInsertSave = new JButton("SAVE");
		btnCustomerInsertSave.setBounds(200, 350, 200, 30);
		btnCustomerInsertSave.setBackground(Color.decode("#F2F2F2"));
		customerInsertPn.add(btnCustomerInsertSave);

		customerInsertPn.setVisible(false);

		btnCustomerInsertSave.addActionListener(this);

	}


	/* ----------------Client테이블 보여주기 패널------------------------ */
	public void customer() {

		customerPn = new JPanel();
		customerPn.setBounds(200, 100, 800, 500);
		customerPn.setBackground(Color.decode("#F2DAD8"));
		customerPn.setLayout(null);
		managerPanel.add(customerPn);

		lblCustomer = new JLabel("Customer information management");
		lblCustomer.setBounds(130, 35, 600, 50);
		lblCustomer.setFont(new Font("", Font.BOLD, 30));
		customerPn.add(lblCustomer);

		clientTable = new JTable();
		clientScroll = new JScrollPane(clientTable);
		clientScroll.setBounds(100, 100, 600, 250);
		customerPn.add(clientScroll);

		showClient();

		btnCustomerInsert = new JButton("INSERT");
		btnCustomerInsert.setBounds(130, 400, 100, 30);
		btnCustomerInsert.setBackground(Color.decode("#F2F2F2"));
		customerPn.add(btnCustomerInsert);

		btnCustomerEdit = new JButton("EDIT");
		btnCustomerEdit.setBounds(330, 400, 100, 30);
		btnCustomerEdit.setBackground(Color.decode("#F2F2F2"));
		customerPn.add(btnCustomerEdit);

		btnCustomerDelete = new JButton("DELETE");
		btnCustomerDelete.setBounds(530, 400, 100, 30);
		btnCustomerDelete.setBackground(Color.decode("#F2F2F2"));
		customerPn.add(btnCustomerDelete);

		customerPn.setVisible(false);

		btnCustomerInsert.addActionListener(this);
		btnCustomerEdit.addActionListener(this);
		btnCustomerDelete.addActionListener(this);

	}

	/*-----------------------DBConnection--------------------------------*/
	public void conDB() {
		try {
			Class.forName(Driver);
			System.out.println("드라이버 로드 성공");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		try { /* 데이터베이스를 연결하는 과정 */
			System.out.println("데이터베이스 연결 준비...");
			con = DriverManager.getConnection(url, userid, pwd);
			System.out.println("데이터베이스 연결 성공");

		} catch (SQLException e1) {
			e1.printStackTrace();
		}

	}
	/*-----------------클래스형 변수 --------------------------*/

	public class Repair {
		int id;
		String content;
		String date;// 수리날짜
		int charge;
		String dueDate;// 납입기한
		String otherRepairInfo;
		int companyId;
		String clientId;
		int carId;
		int shopId;
	}

	public class Test {
		int id;
		String front;
		String left;
		String right;
		String back;
		String fixNeed;
		int carId;
		int rentId;
	}

	public class Repairshop {
		int id;
		String name;
		String addr;
		String num;
		String agentName;
		String agentEmail;
	}

	public class Company {
		int id;
		String name;
		String addr;
		String num;
		String agentName;
		String agentEmail;
	}

	public class Client {
		String id;
		String name;
		String addr;
		String num;
		String email;
	}

	public class Car {
		int id;
		String name;
		String num;
		int max;
		String madeBy;
		int madeYear;
		int distance;
		int price;
		String campingcarDate;
		int possible;
		int companyID;
	}

	public class Rent {
		int rentId;
		String startDate;
		int period;
		int charge;
		String returnDate;
		String otherCharge;
		String chargeInform;
		int carId;
		String clientId;
		int companyId;
	}

	/*-------------------------------------DB동작 메소드----------------------------------------------*/


	/*----------------Rent테이블의  returnDate 갱신을 위한 메소드-------------------------*/
	public void updateRentalHistory(String id) {
		String sql = "update rent set returnDate=curdate() where campingcar_campingcarid=? and returnDate is null";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, Integer.parseInt(id));
			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

			try {
				if (pstmt != null && !pstmt.isClosed())
					pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/*----------------Campingcar테이블의 possible항목 갱신을 위한 메소드-------------------------*/
	public void updateCarState(String state, String id) {
		String sql = null;
		if (state.equals("RENT"))
			sql = "update campingcar set campingcarPossible=0 where campingcarid=?";
		else if (state.equals("RETURN"))
			sql = "update campingcar set campingcarPossible=1 where campingcarId=?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, Integer.parseInt(id));
			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JOptionPane.showMessageDialog(null, "COMPLETE " + state);
			try {
				if (pstmt != null && !pstmt.isClosed())
					pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/*----------------RepairInfo테이블의 삽입/삭제/변경 을 위한 메소드-------------------------*/
	public boolean manipulateRepair(String state) {
		boolean result = false;
		String sql = null;
		if (state.equals("INSERT"))
			sql = "insert into repairInfo(repairInfo, company_companyId, client_clientid, campingcar_campingcarId, repairshop_repairshopId) "
					+ "values((select if((select max(repairInfo) from repairInfo t) is null, 1, (select max(repairInfo) from repairInfo t)+1))"
					+ ",?,?,?,?)";
		else if (state.equals("EDIT"))
			sql = "update repairInfo set repairContent=?,repairDate=?,repairCharge=?,dueDate=?,"
					+ "otherRepairInfo=? where repairInfo=?";
		else
			sql = "delete from repairInfo where repairInfo=?";// 여기
		try {
			pstmt = con.prepareStatement(sql);
			if (state.equals("INSERT")) {
				pstmt.setString(1, txtCompanyID_test.getText());
				pstmt.setString(2, txtClientID_test.getText());
				pstmt.setString(3, txtCarID_test.getText());
				pstmt.setString(4, txtShopID_test.getText());
			} else if (state.equals("EDIT")) {
				pstmt.setString(1, txtContent_repair.getText());
				pstmt.setString(2, txtDate_repair.getText());
				pstmt.setString(3, txtCharge_repair.getText());
				pstmt.setString(4, txtDue_repair.getText());
				pstmt.setString(5, txtOthers_repair.getText());
				pstmt.setString(6, (String) repairHistoryTable.getValueAt(repairHistoryTable.getSelectedRow(), 0));
			} else if (state.equals("DELETE")) {
				pstmt.setString(1, (String) repairHistoryTable.getValueAt(repairHistoryTable.getSelectedRow(), 0));
			}
			if (state.equals("DELETE") || state.equals("INSERT")) {
				pstmt.executeUpdate();
				JOptionPane.showMessageDialog(null, "COMPLETE " + state);
				result = true;

			} else if (!isInt(txtCharge_repair.getText()))
				JOptionPane.showMessageDialog(null, "도메인 무결성 위배: 수리비용에 숫자를 입력해주세요!");
			else if (!validationDate(txtDate_repair.getText()) || !validationDate(txtDue_repair.getText())) {
				JOptionPane.showMessageDialog(null, "도메인 무결성 위배: 날짜형식은 yyyy-mm-dd입니다.");
			} else {
				JOptionPane.showMessageDialog(null, "Complete " + state);
				pstmt.executeUpdate();
				result = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null && !pstmt.isClosed())
					pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}


	/*----------------Test테이블의 삽입을 위한 메소드-------------------------*/
	public boolean manipulateTest() {
		boolean result = true;
		String sql = "insert into test values((select if((select max(testid) from test t) is null, 1, (select max(testid) from test t)+1))"
				+ ",?,?,?,?,?,?,?)";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, txtTestFront.getText());
			pstmt.setString(2, txtTestLeft.getText());
			pstmt.setString(3, txtTestRight.getText());
			pstmt.setString(4, txtTestBack.getText());
			if (fixNeed.isSelected() == true)
				pstmt.setString(5, "yes");
			else
				pstmt.setString(5, "no");
			pstmt.setString(6, txtCarID_test.getText());
			pstmt.setString(7, txtRentID_test.getText());

			if (checkIntegrity("repairShop", "repairShopId", txtShopID_test.getText()) || !fixNeed.isSelected()) {
				pstmt.executeUpdate();
				JOptionPane.showMessageDialog(null, "점검 완료!");
			} else {
				JOptionPane.showMessageDialog(null, "참조 무결성 위배: 존재하는 정비소가 없습니다.!");// 여기
				result = false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

			try {
				if (pstmt != null && !pstmt.isClosed())
					pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	/*----------------RepairShop테이블의 삽입/삭제/변경 을 위한 메소드-------------------------*/
	public void manipulateRepairshop(String state) {
		String sql = null;
		if (state.equals("INSERT"))
			sql = "insert into repairShop values((select if((select max(repairshopId) from repairShop t) is null, 1, (select max(repairShopId) from repairShop t)+1)),?,?,?,?,?)";
		else if (state.equals("EDIT"))
			sql = "update repairShop set repairShopName=?,repairShopAddress=?,repairShopNum=?,"
					+ "repairShopAgentName=?, repairShopEmail=? where repairShopId=?";
		else if (state.equals("DELETE"))
			sql = "delete from repairShop where repairShopId=?";
		try {
			pstmt = con.prepareStatement(sql);
			if (!state.equals("DELETE")) {
				pstmt.setString(1, txtShopName.getText());
				pstmt.setString(2, txtShopAddr.getText());
				pstmt.setString(3, txtShopNum.getText());
				pstmt.setString(4, txtShopAgent.getText());
				pstmt.setString(5, txtShopEmail.getText());
				if (state == "EDIT")
					pstmt.setString(6, (String) repairshopTable.getValueAt(repairshopTable.getSelectedRow(), 0));
			} else {
				pstmt.setString(1, (String) repairshopTable.getValueAt(repairshopTable.getSelectedRow(), 0));
			}

			JOptionPane.showMessageDialog(null, "COMPLETE " + state);
			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null && !pstmt.isClosed())
					pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/*----------------Campingcar테이블의 삽입/삭제/변경 을 위한 메소드-------------------------*/
	public void manipulateCar(String state) {
		String sql = null;
		if (state.equals("INSERT"))
			sql = "insert into campingcar values((select if((select max(campingcarid) from campingcar t) is null, 1, (select max(campingcarid) from campingcar t)+1)),?,?,?,?,?,?,?,?,?,?)";
		else if (state.equals("EDIT"))
			sql = "update campingcar set campingcarName=?,campingcarNum=?,campingcarMax=?,"
					+ "madeBy=?, madeYear=?,distance=?,price=?,campingcarDate=?,"
					+ "campingcarPossible=?,company_companyid=? where campingcarId=?";
		else if (state.equals("DELETE"))
			sql = "delete from campingcar where campingcarId=?";
		try {
			pstmt = con.prepareStatement(sql);
			if (!state.equals("DELETE")) {
				pstmt.setString(1, txtCarName.getText());
				pstmt.setString(2, txtCarNum.getText());
				pstmt.setString(3, txtCarMax.getText());
				pstmt.setString(4, txtCarMade.getText());
				pstmt.setString(5, txtCarYear.getText());
				pstmt.setString(6, txtCarDistance.getText());
				pstmt.setString(7, txtCarPrice.getText());
				pstmt.setString(8, txtCarDate.getText());
				pstmt.setString(9, txtCarPossible.getText());
				pstmt.setString(10, txtCarCompany.getText());
				if (state == "EDIT")
					pstmt.setString(11, (String) carTable.getValueAt(carTable.getSelectedRow(), 0));
			} else {
				pstmt.setString(1, (String) carTable.getValueAt(carTable.getSelectedRow(), 0));
			}
			if (state.equals("DELETE")) {
				pstmt.executeUpdate();
				JOptionPane.showMessageDialog(null, "COMPLETE " + state);
			} else {
				if (!checkIntegrity("company", "companyId", txtCarCompany.getText()))
					JOptionPane.showMessageDialog(null, "참조 무결성 위배: 해당 대여회사가 존재하지 않습니다.");
				else if (!isInt(txtCarMax.getText()) || !isInt(txtCarYear.getText()) || !isInt(txtCarDistance.getText())
						|| !isInt(txtCarPrice.getText()) || !isInt(txtCarCompany.getText()))
					JOptionPane.showMessageDialog(null, "도메인 무결성 위배!!");
				else if (isNull(txtCarMax.getText()) || isNull(txtCarYear.getText()) || isNull(txtCarDistance.getText())
						|| isNull(txtCarPrice.getText()) || isNull(txtCarCompany.getText())) {
					JOptionPane.showMessageDialog(null, "최대승차인원/제조년도/누적주행거리/가격 항목을 입력해주세요~");
				} else {
					pstmt.executeUpdate();
					JOptionPane.showMessageDialog(null, "COMPLETE " + state);
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

			try {
				if (pstmt != null && !pstmt.isClosed())
					pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/*----------------Company테이블의 삽입/삭제/변경 을 위한 메소드-------------------------*/
	public void manipulateCompany(String state) {
		String sql = null;
		if (state.equals("INSERT"))
			sql = "insert into company values((select if((select max(companyid) from company t) is null, 1, (select max(companyid) from company t)+1)),?,?,?,?,?)";
		else if (state.equals("EDIT"))
			sql = "update company set companyName=?,address=?,phoneNum=?,"
					+ "AgentName=?, AgentEmail=? where companyId=?";
		else if (state.equals("DELETE"))
			sql = "delete from company where companyId=?";
		try {
			pstmt = con.prepareStatement(sql);
			if (!state.equals("DELETE")) {
				pstmt.setString(1, txtCompanyName.getText());
				pstmt.setString(2, txtCompanyAddr.getText());
				pstmt.setString(3, txtCompanyNum.getText());
				pstmt.setString(4, txtCompanyAgent.getText());
				pstmt.setString(5, txtCompanyAgent_e.getText());
				if (state == "EDIT")
					pstmt.setString(6, (String) companyTable.getValueAt(companyTable.getSelectedRow(), 0));
			} else {
				pstmt.setString(1, (String) companyTable.getValueAt(companyTable.getSelectedRow(), 0));
			}

			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JOptionPane.showMessageDialog(null, "Complete " + state);
			try {
				if (pstmt != null && !pstmt.isClosed())
					pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	/*----------------Client테이블의 삽입/삭제/변경 을 위한 메소드-------------------------*/
	public void manipulateClient(String state) {
		String sql = null;
		if (state.equals("INSERT"))
			sql = "insert into client values(?,?,?,?,?)";
		else if (state.equals("EDIT"))
			sql = "update client set clientId=?,clientName=?,clientAddress=?,"
					+ "clientPhoneNum=?, clientEmail=? where clientid=?";
		else if (state.equals("DELETE"))
			sql = "delete from client where clientid=?";
		try {
			PreparedStatement pstmt = con.prepareStatement(sql);
			if (!state.equals("DELETE")) {
				pstmt.setString(1, txtUserId.getText());
				pstmt.setString(2, txtUserName.getText());
				pstmt.setString(3, txtUserAddr.getText());
				pstmt.setString(4, txtUserNum.getText());
				pstmt.setString(5, txtUserEmail.getText());
				if (state == "EDIT")
					pstmt.setString(6, (String) clientTable.getValueAt(clientTable.getSelectedRow(), 0));
			} else {
				pstmt.setString(1, (String) clientTable.getValueAt(clientTable.getSelectedRow(), 0));
			}
			if ((!checkIntegrity("client", "clientId", txtUserId.getText())) || state == "DELETE" || state == "EDIT")//
			{
				pstmt.executeUpdate();
				JOptionPane.showMessageDialog(null, state + " COMPLETE!");
			} else
				JOptionPane.showMessageDialog(null, "개체 무결성  위배: 이미 존재하는 운전면허증 번호 입니다.");

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

			try {
				if (pstmt != null && !pstmt.isClosed())
					pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	/* -----------------사용자가 해당 캠핑카를 렌트할 때 동작하는 메소드------------------------ */
	public boolean insertRentInfo() {
		String sql = "INSERT INTO RENT(rentId,StartDate,Period, Charge,Campingcar_campingcarID, "
				+ "client_clientID, company_companyID) VALUES((select if((select max(rentid) from rent t) is null, 1, (select max(rentid) from rent t)+1))"
				+ ",?,?,?*(select price from campingcar where "
				+ "campingcarid=?),?,?,(select company_companyid from campingcar where campingcarid=?))";
		boolean result = false;
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, txtRentStart.getText());
			pstmt.setString(2, txtRentPeriod.getText());
			pstmt.setString(3, txtRentPeriod.getText());
			pstmt.setString(4, (String) userCarTable.getValueAt(userCarTable.getSelectedRow(), 0));
			pstmt.setString(5, (String) userCarTable.getValueAt(userCarTable.getSelectedRow(), 0));
			pstmt.setString(6, txtClientID.getText());
			pstmt.setString(7, (String) userCarTable.getValueAt(userCarTable.getSelectedRow(), 0));

			// 개체 무결성 검사 - clientID 가 중복되는지 확인
			if (!checkIntegrity("Client", "ClientID", txtClientID.getText())) // 존재하면 true 아니면 false
				JOptionPane.showMessageDialog(null, "참조 무결성 위배:운전면허증번호를 확인해주세요!");
			else if (!isInt(txtRentPeriod.getText()))
				JOptionPane.showMessageDialog(null, "도메인 무결성 위배:승차인원 필드에 숫자를 입력해주세요.");
			else if (!validationDate(txtRentStart.getText()))
				JOptionPane.showMessageDialog(null, "도메인 무결성 위배:날짜 형식은 yyyy-mm-dd 입니다.");
			else {
				pstmt.executeUpdate();
				result = true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null && !pstmt.isClosed())
					pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;

	}


	/* -----------------테이블 구성을 위해 SELECT하는 메소드------------------------ */
	public ArrayList<Car> selectSearch1(int type) {
		ArrayList<Car> arrCar = new ArrayList<Car>();
		String sql = null;
		if (type == 1) {
			sql = "SELECT * FROM CampingCar WHERE price < 70000 AND "
					+ "CampingcarId IN (SELECT Campingcar_CampingcarId FROM rent Group by "
					+ "Campingcar_CampingCarid Having count(*) >= 2)";
		} else {
			sql = "SELECT * FROM CampingCar Where CampingcarPossible = 1 AND CampingcarId IN"
					+ " (SELECT Campingcar_CampingcarId FROM repairinfo Group by Campingcar_"
					+ "CampingcarId Having count(*) <= 1)";
		}
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Car tmpCar = new Car();
				tmpCar.id = rs.getInt("campingcarId");
				tmpCar.name = rs.getString("campingcarName");
				tmpCar.num = rs.getString("campingcarNum");
				tmpCar.max = rs.getInt("campingcarMax");
				tmpCar.madeBy = rs.getString("madeBy");
				tmpCar.madeYear = rs.getInt("madeYear");
				tmpCar.distance = rs.getInt("distance");
				tmpCar.price = rs.getInt("price");
				tmpCar.campingcarDate = rs.getString("campingcarDate");
				tmpCar.possible = rs.getInt("campingcarPossible");
				tmpCar.companyID = rs.getInt("company_companyid");
				arrCar.add(tmpCar);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null && !stmt.isClosed())
					stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return arrCar;
	}

	public ArrayList<Repairshop> selectRepairshop(String type) {
		ArrayList<Repairshop> arrShop = new ArrayList<Repairshop>();
		String sql = null;
		if (type.equals(""))
			sql = "select * from Repairshop";
		else
			sql = "SELECT * FROM Repairshop Where RepairShopId IN "
					+ "(SELECT RepairShop_RepairShopId FROM Repairinfo WHERE RepairCharge > 200000)";
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Repairshop tmpShop = new Repairshop();
				tmpShop.id = rs.getInt("repairshopId");
				tmpShop.name = rs.getString("repairshopName");
				tmpShop.addr = rs.getString("repairshopAddress");
				tmpShop.num = rs.getString("repairshopNum");
				tmpShop.agentName = rs.getString("repairshopAgentName");
				tmpShop.agentEmail = rs.getString("repairshopEmail");
				arrShop.add(tmpShop);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null && !stmt.isClosed())
					stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return arrShop;
	}

	public ArrayList<Car> selectCar(int state, String company) {
		ArrayList<Car> arrCar = new ArrayList<Car>();
		PreparedStatement pstmt = null;
		String sql = null;
		if (state == 0) {
			sql = "select * from campingcar";
		} else if (state == 1) {
			if (company.equals(""))
				sql = "select * from campingcar where campingcarPossible=1";
			else
				sql = "select * from campingcar where campingcarPossible=1 and campingcarName=?";
		}
		try {
			pstmt = con.prepareStatement(sql);
			if (state == 1 && !company.equals(""))
				pstmt.setString(1, company);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Car tmpCar = new Car();
				tmpCar.id = rs.getInt("campingcarId");
				tmpCar.name = rs.getString("campingcarName");
				tmpCar.num = rs.getString("campingcarNum");
				tmpCar.max = rs.getInt("campingcarMax");
				tmpCar.madeBy = rs.getString("madeBy");
				tmpCar.madeYear = rs.getInt("madeYear");
				tmpCar.distance = rs.getInt("distance");
				tmpCar.price = rs.getInt("price");
				tmpCar.campingcarDate = rs.getString("campingcarDate");
				tmpCar.possible = rs.getInt("campingcarPossible");
				tmpCar.companyID = rs.getInt("company_companyid");
				arrCar.add(tmpCar);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null && !pstmt.isClosed())
					pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return arrCar;
	}

	public ArrayList<Company> selectCompany(String type) {
		ArrayList<Company> arrCompany = new ArrayList<Company>();
		String sql = null;
		if (type.equals(""))
			sql = "select * from Company";
		else
			sql = "SELECT * FROM Company WHERE CompanyId IN"
					+ " (SELECT Company_companyid FROM Campingcar WHERE CampingcarMax >= 5 AND CampingCarID IN"
					+ " (SELECT distinct Campingcar_CampingcarId FROM repairinfo r1 WHERE 510000 >="
					+ " (Select SUM(RepairCharge) FROM repairinfo r2 WHERE r1.Campingcar_CampingcarId = "
					+ "r2.Campingcar_CampingcarId)))";
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Company tmpCompany = new Company();
				tmpCompany.id = rs.getInt("companyid");
				tmpCompany.name = rs.getString("companyname");
				tmpCompany.num = rs.getString("phonenum");
				tmpCompany.addr = rs.getString("address");
				tmpCompany.agentName = rs.getString("agentname");
				tmpCompany.agentEmail = rs.getString("agentemail");
				arrCompany.add(tmpCompany);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null && !stmt.isClosed())
					stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return arrCompany;
	}

	public ArrayList<Rent> selectRental(String type) {
		ArrayList<Rent> arrRent = new ArrayList<Rent>();
		String sql = null;
		if (type.equals("RETURN"))
			sql = "select * from rent where returnDate is null";
		else
			sql = "select * from Rent";
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Rent tmpRent = new Rent();
				tmpRent.rentId = rs.getInt("rentId");
				tmpRent.startDate = rs.getString("startDate");
				tmpRent.period = rs.getInt("period");
				tmpRent.charge = rs.getInt("charge");
				tmpRent.returnDate = rs.getString("returnDate");
				tmpRent.otherCharge = rs.getString("otherCharge");
				tmpRent.chargeInform = rs.getString("chargeInform");
				tmpRent.carId = rs.getInt("campingcar_campingcarId");
				tmpRent.clientId = rs.getString("client_clientId");
				tmpRent.companyId = rs.getInt("company_companyId");

				arrRent.add(tmpRent);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null && !stmt.isClosed())
					stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return arrRent;
	}

	public ArrayList<Repair> selectRepair(String state) {
		ArrayList<Repair> arrRepair = new ArrayList<Repair>();
		String sql = null;
		if (state.equals("REQUEST"))
			sql = "select * from repairInfo where repairDate is null";
		else
			sql = "select * from repairInfo";
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Repair tmpRepair = new Repair();
				tmpRepair.id = rs.getInt("repairInfo");
				tmpRepair.content = rs.getString("repairContent");
				tmpRepair.date = rs.getString("repairDate");
				tmpRepair.charge = rs.getInt("repairCharge");
				tmpRepair.dueDate = rs.getString("dueDate");
				tmpRepair.otherRepairInfo = rs.getString("otherRepairInfo");
				tmpRepair.companyId = rs.getInt("company_companyId");
				tmpRepair.clientId = rs.getString("client_clientId");
				tmpRepair.carId = rs.getInt("campingcar_campingcarId");
				tmpRepair.shopId = rs.getInt("repairShop_repairShopId");
				arrRepair.add(tmpRepair);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null && !stmt.isClosed())
					stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return arrRepair;

	}

	public ArrayList<Client> selectClient() {
		ArrayList<Client> arrClient = new ArrayList<Client>();
		String sql = "select * from Client";
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Client tmpClient = new Client();
				tmpClient.id = rs.getString("clientid");
				tmpClient.name = rs.getString("clientname");
				tmpClient.num = rs.getString("clientaddress");
				tmpClient.addr = rs.getString("clientphonenum");
				tmpClient.email = rs.getString("clientemail");
				arrClient.add(tmpClient);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null && !stmt.isClosed())
					stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return arrClient;
	}

	// 버튼 클릭 메소드
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		// 모드 선택 액션
		if (e.getSource() == btnInit) {
			initDB();
			userPanel.setVisible(false);
			managerPanel.setVisible(false);
			initPanel.setVisible(true);
		}
		if (e.getSource() == btnBack || e.getSource() == btnUserBack) {
			userPanel.setVisible(false);
			managerPanel.setVisible(false);
			initPanel.setVisible(true);
		}

		if (e.getSource() == userMod) {
			mode = "USER";
			initPanel.setVisible(false);
			managerPanel.setVisible(false);
			userPanel.setVisible(true);
			showCampingcar(1,"");

		} else if (e.getSource() == manageMod) {
			mode = "MANAGER";
			initPanel.setVisible(false);
			userPanel.setVisible(false);
			managerPanel.setVisible(true);
		}
		if (e.getSource() == btnSearchCar) {
			changePanel(userCarPn);
			showCampingcar(1, "");
		}

		/* 메니저 모드 메뉴바 선택 */
		if (e.getSource() == btnCustomer) {
			changePanel(customerPn);
			showClient();
		} else if (e.getSource() == btnCompany) {
			changePanel(companyPn);
			showCompany("");
		} else if (e.getSource() == btnCampingcar) {
			changePanel(carPn);
			showCampingcar(0, "");

		} else if (e.getSource() == btnRepairshop) {
			changePanel(repairPn);
			showRepairshop("");
		} else if (e.getSource() == btnRental) {
			changePanel(rentalPn);
			showRentalHistory("");
		} else if (e.getSource() == btnRepair) {
			changePanel(repairHistoryPn);
			showRepairHistory("");
		} else if (e.getSource() == search) {
			changePanel(searchPn);
		}

		/* 유저모드에서 유저가 campingcar를 빌릴때 필요한 마우스 함수 */
		if (e.getSource() == btnUserRent) {
			if (checkSelected(userCarTable)) {
				changePanel(userRentPn);
				txtClientID.setText(null);
				txtRentStart.setText(null);

				txtRentPeriod.setText(null);
			} else
				JOptionPane.showMessageDialog(null, "row를 클릭해주세요!!");
		} else if (e.getSource() == btnRentSave) {
			if (insertRentInfo())
				updateCarState("RENT", (String) userCarTable.getValueAt(userCarTable.getSelectedRow(), 0));
			showCampingcar(1, "");
			changePanel(userCarPn);
		} else if (e.getSource() == btnUserSearch) {
			String tmp = txtUserSearch.getText();
			showCampingcar(1, tmp);
		}

		// manager 메뉴 - customer 버튼
		if (e.getSource() == btnCustomerEdit) {
			state = "EDIT";
			if (checkSelected(clientTable)) {
				changePanel(customerInsertPn);
				txtUserId.setText((String) clientTable.getValueAt(clientTable.getSelectedRow(), 0));
				txtUserId.setEditable(false);
				txtUserName.setText((String) clientTable.getValueAt(clientTable.getSelectedRow(), 1));
				txtUserAddr.setText((String) clientTable.getValueAt(clientTable.getSelectedRow(), 2));
				txtUserNum.setText((String) clientTable.getValueAt(clientTable.getSelectedRow(), 3));
				txtUserEmail.setText((String) clientTable.getValueAt(clientTable.getSelectedRow(), 4));

			} else {
				JOptionPane.showMessageDialog(null, "수정할 row를 선택해주세요.");
			}
		} else if (e.getSource() == btnCustomerInsert) {
			state = "INSERT";
			changePanel(customerInsertPn);
			txtUserId.setEditable(true);
			txtUserId.setText(null);
			txtUserName.setText(null);
			txtUserAddr.setText(null);
			txtUserNum.setText(null);
			txtUserEmail.setText(null);

		} else if (e.getSource() == btnCustomerDelete) {
			if (checkSelected(clientTable)) {
				state = "DELETE";
				manipulateClient(state);
				showClient();
			} else
				JOptionPane.showMessageDialog(null, "삭제할 row를 선택해주세요.");
		} else if (e.getSource() == btnCustomerInsertSave) {
			manipulateClient(state);
			changePanel(customerPn);
			showClient();
		}

		// manager메뉴 - company 버튼
		if (e.getSource() == btnCompanyInsert) {
			state = "INSERT";
			txtCompanyName.setText(null);
			txtCompanyAddr.setText(null);
			txtCompanyNum.setText(null);
			txtCompanyAgent.setText(null);
			txtCompanyAgent_e.setText(null);
			changePanel(companyInsertPn);
		} else if (e.getSource() == btnCompanyEdit) {
			state = "EDIT";

			if (checkSelected(companyTable)) {
				changePanel(companyInsertPn);
				txtCompanyName.setText((String) companyTable.getValueAt(companyTable.getSelectedRow(), 1));
				txtCompanyAddr.setText((String) companyTable.getValueAt(companyTable.getSelectedRow(), 2));
				txtCompanyNum.setText((String) companyTable.getValueAt(companyTable.getSelectedRow(), 3));
				txtCompanyAgent.setText((String) companyTable.getValueAt(companyTable.getSelectedRow(), 4));
				txtCompanyAgent_e.setText((String) companyTable.getValueAt(companyTable.getSelectedRow(), 5));
			} else {
				JOptionPane.showMessageDialog(null, "수정할 row를 선택해주세요.");
			}
		} else if (e.getSource() == btnCompanyDelete) {
			if (checkSelected(companyTable)) {
				state = "DELETE";
				manipulateCompany(state);
				showCompany("");
			} else
				JOptionPane.showMessageDialog(null, "삭제할 row를 선택해주세요!");
		} else if (e.getSource() == btnCompanySave) {
			manipulateCompany(state);
			showCompany("");
			changePanel(companyPn);
		}
		if (e.getSource() == btnCarInsert) {
			state = "INSERT";
			changePanel(carInsertPn);
			txtCarName.setText(null);
			txtCarNum.setText(null);
			txtCarMax.setText(null);
			txtCarMade.setText(null);
			txtCarYear.setText(null);
			txtCarDistance.setText(null);
			txtCarPrice.setText(null);
			txtCarPossible.setText("1");
			Date date = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd ", Locale.KOREA);
			Date currentTime = new Date();
			String dTime = formatter.format(currentTime);
			txtCarDate.setText(dTime);
			txtCarCompany.setText(null);

		} else if (e.getSource() == btnCarEdit) {
			state = "EDIT";
			if (checkSelected(carTable)) {
				changePanel(carInsertPn);
				txtCarName.setText((String) carTable.getValueAt(carTable.getSelectedRow(), 1));
				txtCarNum.setText((String) carTable.getValueAt(carTable.getSelectedRow(), 2));
				txtCarMax.setText((String) carTable.getValueAt(carTable.getSelectedRow(), 3));
				txtCarMade.setText((String) carTable.getValueAt(carTable.getSelectedRow(), 4));
				txtCarYear.setText((String) carTable.getValueAt(carTable.getSelectedRow(), 5));
				txtCarDistance.setText((String) carTable.getValueAt(carTable.getSelectedRow(), 6));
				txtCarPrice.setText((String) carTable.getValueAt(carTable.getSelectedRow(), 7));
				txtCarDate.setText((String) carTable.getValueAt(carTable.getSelectedRow(), 8));
				txtCarPossible.setText((String) carTable.getValueAt(carTable.getSelectedRow(), 9));
				txtCarCompany.setText((String) carTable.getValueAt(carTable.getSelectedRow(), 10));
			} else {
				JOptionPane.showMessageDialog(null, "수정할 row를 선택해주세요.");
			}

		} else if (e.getSource() == btnCarDelete) {
			if (checkSelected(carTable)) {
				state = "DELETE";
				manipulateCar(state);
				showCampingcar(0, "");
			} else
				JOptionPane.showMessageDialog(null, "삭제할 row를 선택해주세여.");

		} else if (e.getSource() == btnCarSave) {
			manipulateCar(state);
			showCampingcar(0, "");
			changePanel(carPn);
			/* 정비소 관한 거 */
		} else if (e.getSource() == btnRepairInsert) {
			state = "INSERT";
			changePanel(repairInsertPn);
			txtShopName.setText(null);
			txtShopAddr.setText(null);
			txtShopNum.setText(null);
			txtShopAgent.setText(null);
			txtShopEmail.setText(null);
		} else if (e.getSource() == btnRepairEdit) {
			state = "EDIT";

			if (checkSelected(repairshopTable)) {
				changePanel(repairInsertPn);
				txtShopName.setText((String) repairshopTable.getValueAt(repairshopTable.getSelectedRow(), 1));
				txtShopAddr.setText((String) repairshopTable.getValueAt(repairshopTable.getSelectedRow(), 2));
				txtShopNum.setText((String) repairshopTable.getValueAt(repairshopTable.getSelectedRow(), 3));
				txtShopAgent.setText((String) repairshopTable.getValueAt(repairshopTable.getSelectedRow(), 4));
				txtShopEmail.setText((String) repairshopTable.getValueAt(repairshopTable.getSelectedRow(), 5));
			} else {
				JOptionPane.showMessageDialog(null, "수정할 row를 선택해주세요.");
			}
		} else if (e.getSource() == btnRepairDelete) {
			if (checkSelected(repairshopTable)) {
				state = "DELETE";
				manipulateRepairshop(state);
				showRepairshop("");
			} else
				JOptionPane.showMessageDialog(null, "삭제할 row를 선택해주세요.");
		} else if (e.getSource() == btnRepairSave) {
			manipulateRepairshop(state);
			changePanel(repairPn);
			showRepairshop("");

			/* 캠핑카 반환! */
		} else if (e.getSource() == btnReturn) {
			if (checkSelected(rentTable)) {
				if (!isReturned((String) rentTable.getValueAt(rentTable.getSelectedRow(), 5))) {
					changePanel(testPn);
					txtRentID_test.setText((String) rentTable.getValueAt(rentTable.getSelectedRow(), 0));
					txtCarID_test.setText((String) rentTable.getValueAt(rentTable.getSelectedRow(), 5));
					txtClientID_test.setText((String) rentTable.getValueAt(rentTable.getSelectedRow(), 6));
					txtCompanyID_test.setText((String) rentTable.getValueAt(rentTable.getSelectedRow(), 7));
					txtTestFront.setText(null);
					txtTestRight.setText(null);
					txtTestLeft.setText(null);
					txtTestBack.setText(null);
					txtShopID_test.setText(null);
					fixNeed.setSelected(false);

				} else
					JOptionPane.showMessageDialog(null, "이미 반환된 캠핑카입니다!");
			} else
				JOptionPane.showMessageDialog(null, "반환할 캠핑카를 선택하세요.");

			/* 점검 & 수리 */
		} else if (e.getSource() == btnCheckReturn) {
			showRentalHistory("RETURN");
		} else if (e.getSource() == fixNeed) {
			if (fixNeed.isSelected()) {
				txtShopID_test.setEditable(true);
			} else
				txtShopID_test.setEditable(false);
		} else if (e.getSource() == btnSave_test) {
			if (manipulateTest()) {
				if (fixNeed.isSelected() == true) {
					manipulateRepair("INSERT");// 정비정보를 삽입==정비를 요청
				} else {
					updateCarState("RETURN", (String) rentTable.getValueAt(rentTable.getSelectedRow(), 5));
					updateRentalHistory((String) rentTable.getValueAt(rentTable.getSelectedRow(), 5));
				}
			}
			showRentalHistory("");
			changePanel(rentalPn);

		} else if (e.getSource() == btnEdit_RH) {// repair history에 관한 마우스
			if (checkSelected(repairHistoryTable)) {
				changePanel(rePn);
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd ", Locale.KOREA);
				Date currentTime = new Date();
				String dTime = formatter.format(currentTime);
				Calendar cal = Calendar.getInstance();
				cal.setTime(currentTime);
				cal.add(Calendar.DATE, +7);
				String today = formatter.format(cal.getTime());

				txtContent_repair
						.setText((String) repairHistoryTable.getValueAt(repairHistoryTable.getSelectedRow(), 1));
				txtCharge_repair
						.setText((String) repairHistoryTable.getValueAt(repairHistoryTable.getSelectedRow(), 3));
				txtOthers_repair
						.setText((String) repairHistoryTable.getValueAt(repairHistoryTable.getSelectedRow(), 5));
				txtDate_repair.setText(dTime);
				txtDue_repair.setText(today);//
			} else
				JOptionPane.showMessageDialog(null, "row를 클릭해주세요!");
		} else if (e.getSource() == btnRequest) {
			showRepairHistory("REQUEST");
		} else if (e.getSource() == btnSave_repair) {
			if (manipulateRepair("EDIT")) {
				if (!isReturned((String) repairHistoryTable.getValueAt(repairHistoryTable.getSelectedRow(), 8))) {// 여깅
					updateCarState("RETURN",
							(String) repairHistoryTable.getValueAt(repairHistoryTable.getSelectedRow(), 8));
					updateRentalHistory((String) repairHistoryTable.getValueAt(repairHistoryTable.getSelectedRow(), 8));
				}
			}
			showRepairHistory("");
			changePanel(repairHistoryPn);
		} else if (e.getSource() == btnDelete_RH) {
			if (isReturned((String) repairHistoryTable.getValueAt(repairHistoryTable.getSelectedRow(), 8))) {
				manipulateRepair("DELETE");
				showRepairHistory("");
			} else
				JOptionPane.showMessageDialog(null, "반환되지 않은 캠핑카입니다!");

		} else if (e.getSource() == search1) {
			showSearch(1);
		} else if (e.getSource() == search2) {
			showSearch(2);
		} else if (e.getSource() == search3) {
			showSearch(3);
		} else if (e.getSource() == search4) {
			showSearch(4);
		}
	}

	public void showClient() {
		ArrayList<Client> tmpClient = new ArrayList<Client>();
		tmpClient = selectClient();
		String[] col = { "LicenseNum", "Name", "Address", "PhoneNumber", "Emial" };
		String[][] data = new String[tmpClient.size()][col.length];
		int r_idx = 0;
		for (Client client : tmpClient) {
			data[r_idx][0] = client.id;
			data[r_idx][1] = client.name;
			data[r_idx][2] = client.addr;
			data[r_idx][3] = client.num;
			data[r_idx][4] = client.email;
			r_idx++;
		}
		customerModel = new DefaultTableModel(data, col);
		clientTable.setModel(customerModel);
	}

	public void showCompany(String type) {
		ArrayList<Company> tmpCompany = new ArrayList<Company>();
		if (type.equals(""))
			tmpCompany = selectCompany(type);
		else
			tmpCompany = selectCompany(type);
		String[] col = { "CompanyID", "Name", "Address", "PhoneNumber", "AgentName", "AgentEmail" };
		String[][] data = new String[tmpCompany.size()][col.length];
		int r_idx = 0;
		for (Company Company : tmpCompany) {
			data[r_idx][0] = Integer.toString(Company.id);
			data[r_idx][1] = Company.name;
			data[r_idx][2] = Company.addr;
			data[r_idx][3] = Company.num;
			data[r_idx][4] = Company.agentName;
			data[r_idx][5] = Company.agentEmail;
			r_idx++;
		}
		companyModel = new DefaultTableModel(data, col);
		if (type.equals(""))
			companyTable.setModel(companyModel);
		else
			searchTable.setModel(companyModel);
	}

	public void showCampingcar(int state, String tmp) {
		ArrayList<Car> tmpCar = new ArrayList<Car>();
		if (state == 0) // ""
			tmpCar = selectCar(0, "");
		else if (state == 1)
			tmpCar = selectCar(1, tmp);
		else if (state == 10)
			tmpCar = selectSearch1(1);
		else if (state == 11)
			tmpCar = selectSearch1(2);

		String[] col = { "CarID", "Name", "Num", "Max", "MadeBy", "MadeYear", "Distance", "Price", "Campingcardate",
				"Possible", "CompanyID" };
		String[][] data = new String[tmpCar.size()][col.length];
		int r_idx = 0;
		for (Car car : tmpCar) {
			data[r_idx][0] = Integer.toString(car.id);
			data[r_idx][1] = car.name;
			data[r_idx][2] = car.num;
			data[r_idx][3] = Integer.toString(car.max);
			data[r_idx][4] = car.madeBy;
			data[r_idx][5] = Integer.toString(car.madeYear);
			data[r_idx][6] = Integer.toString(car.distance);
			data[r_idx][7] = Integer.toString(car.price);
			data[r_idx][8] = car.campingcarDate;
			data[r_idx][9] = Integer.toString(car.possible);
			data[r_idx][10] = Integer.toString(car.companyID);
			r_idx++;
		}

		carModel = new DefaultTableModel(data, col);
		if (state == 1) {
			userCarTable.setModel(carModel);
		} else if (state == 0)
			carTable.setModel(carModel);
		else if (state == 10)
			searchTable.setModel(carModel);
		else if (state == 11)
			searchTable.setModel(carModel);

	}

	public void showRepairshop(String type) {
		ArrayList<Repairshop> tmpShop = new ArrayList<Repairshop>();
		if (type.equals(""))
			tmpShop = selectRepairshop("");
		else
			tmpShop = selectRepairshop(type);
		String[] col = { "RepairshopID", "Name", "Address", "PhoneNumber", "AgentName", "AgentEmail" };
		String[][] data = new String[tmpShop.size()][col.length];
		int r_idx = 0;
		for (Repairshop shop : tmpShop) {
			data[r_idx][0] = Integer.toString(shop.id);
			data[r_idx][1] = shop.name;
			data[r_idx][2] = shop.addr;
			data[r_idx][3] = shop.num;
			data[r_idx][4] = shop.agentName;
			data[r_idx][5] = shop.agentEmail;
			r_idx++;
		}
		repairshopModel = new DefaultTableModel(data, col);
		if (type.equals(""))
			repairshopTable.setModel(repairshopModel);
		else
			searchTable.setModel(repairshopModel);
	}

	public void showRentalHistory(String type) {
		ArrayList<Rent> tmpRent = new ArrayList<Rent>();
		tmpRent = selectRental(type);
		String[] col = { "RentID", "StartDate", "Period", "Charge", "ReturnDate", "CarID", "ClientID", "CompanyID" };
		String[][] data = new String[tmpRent.size()][col.length];
		int r_idx = 0;
		for (Rent rent : tmpRent) {
			data[r_idx][0] = Integer.toString(rent.rentId);
			data[r_idx][1] = rent.startDate;
			data[r_idx][2] = Integer.toString(rent.period);
			data[r_idx][3] = Integer.toString(rent.charge);
			data[r_idx][4] = rent.returnDate;
			data[r_idx][5] = Integer.toString(rent.carId);
			data[r_idx][6] = rent.clientId;
			data[r_idx][7] = Integer.toString(rent.companyId);

			r_idx++;
		}
		rentModel = new DefaultTableModel(data, col);
		rentTable.setModel(rentModel);
	}

	public void showRepairHistory(String state) {
		ArrayList<Repair> tmpRepair = new ArrayList<Repair>();

		tmpRepair = selectRepair(state);
		String[] col = { "ID", "Content", "Date", "Charge", "DueDate", "OtherInfo", "CompanyID", "ClientID", "CarID",
				"ShopID" };
		String[][] data = new String[tmpRepair.size()][col.length];
		int r_idx = 0;
		for (Repair repair : tmpRepair) {
			data[r_idx][0] = Integer.toString(repair.id);
			data[r_idx][1] = repair.content;
			data[r_idx][2] = repair.date;
			data[r_idx][3] = Integer.toString(repair.charge);
			data[r_idx][4] = repair.dueDate;
			data[r_idx][5] = repair.otherRepairInfo;
			data[r_idx][6] = Integer.toString(repair.companyId);
			data[r_idx][7] = repair.clientId;
			data[r_idx][8] = Integer.toString(repair.carId);
			data[r_idx][9] = Integer.toString(repair.shopId);
			r_idx++;
		}
		rhModel = new DefaultTableModel(data, col);
		repairHistoryTable.setModel(rhModel);
	}

	/*------------------검색 버튼 4개 컨트롤 메소드--------------------------*/
	public void showSearch(int type) {
		if (type == 1) {
			showCampingcar(10, "");
			lblSearch1.setVisible(true);
			lblSearch2.setVisible(false);
			lblSearch3.setVisible(false);
			lblSearch4.setVisible(false);
		} else if (type == 2) {
			showCampingcar(11, "");
			lblSearch1.setVisible(false);
			lblSearch2.setVisible(true);
			lblSearch3.setVisible(false);
			lblSearch4.setVisible(false);
		} else if (type == 3) {
			showCompany("SEARCH");
			lblSearch1.setVisible(false);
			lblSearch2.setVisible(false);
			lblSearch3.setVisible(true);
			lblSearch4.setVisible(false);
		} else if (type == 4) {
			showRepairshop("SEARCH");
			lblSearch1.setVisible(false);
			lblSearch2.setVisible(false);
			lblSearch3.setVisible(false);
			lblSearch4.setVisible(true);
		}
	}

	/*----------------------패널 컨트롤 메소드----------------------------------*/
	public void changePanel(JPanel panel) {
		JPanel[] userPn = { userCarPn, userRentPn };
		JPanel[] managerPn = { customerPn, customerInsertPn, companyPn, companyInsertPn, carPn, carInsertPn, repairPn,
				repairInsertPn, rentalPn, repairHistoryPn, testPn, rePn, searchPn };
		if (mode.equals("USER")) {
			for (JPanel pn : userPn) {
				if (pn == panel)
					pn.setVisible(true);
				else
					pn.setVisible(false);
			}
		} else if (mode.equals("MANAGER")) {
			for (JPanel pn : managerPn) {
				if (pn == panel)
					pn.setVisible(true);
				else
					pn.setVisible(false);
			}
		}
	}

	/*-----------------캠핑카가 반환되었는지 확인하는 메소드---------------------------*/
	public boolean isReturned(String id) {
		boolean result = true;
		String sql = "select * from rent where returnDate is null and campingcar_campingcarId=?";
		PreparedStatement pst = null;
		try {
			pst = con.prepareStatement(sql);
			pst.setInt(1, Integer.parseInt(id));
			pst.executeQuery();
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				result = false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

			try {
				if (pst != null && !pst.isClosed())
					pst.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	/*--------------------DB초기화 메소드------------------------------*/
	public void initDB() {
		try {
			stmt = con.createStatement();
			stmt.executeUpdate("drop table if exists test");
			stmt.executeUpdate("drop table if exists Rent");
			stmt.executeUpdate("drop table if exists Repairinfo");
			stmt.executeUpdate("drop table if exists Campingcar");
			stmt.executeUpdate("drop table if exists RepairShop");
			stmt.executeUpdate("drop table if exists Client");
			stmt.executeUpdate("drop table if exists Company");
			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS `Company`" + "(`CompanyId` INT NOT NULL, "
					+ "`CompanyName` VARCHAR(45) NULL," + "`Address` VARCHAR(45) NULL," + "`PhoneNum` VARCHAR(45) NULL,"
					+ "`AgentName` VARCHAR(45) NULL," + "`AgentEmail` VARCHAR(45) NULL,"
					+ "PRIMARY KEY (`CompanyId`))");
			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS `Client`" + "(`ClientId` VARCHAR(40) NOT NULL, "
					+ "`ClientName` VARCHAR(45) NULL," + "`ClientAddress` VARCHAR(45) NULL,"
					+ "`ClientPhoneNum` VARCHAR(45) NULL," + "`ClientEmail` VARCHAR(45) NULL,"
					+ "PRIMARY KEY (`ClientId`))");
			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS `RepairShop`" + "(`RepairShopId` INT NOT NULL,"
					+ "`RepairShopName` VARCHAR(45) NULL," + "`RepairShopaddress` VARCHAR(45) NULL,"
					+ "`RepairShopnum` VARCHAR(45) NULL," + "`RepairShopagentname` VARCHAR(45) NULL,"
					+ "`RepairShopemail` VARCHAR(45) NULL," + "PRIMARY KEY (`RepairShopId`))");
			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS `Campingcar`" + "(`CampingcarId` INT NOT NULL,"
					+ "`CampingcarName` VARCHAR(45) NULL," + "`CampingcarNum` VARCHAR(45) NULL,"
					+ "`CampingcarMax` INT NULL," + "`MadeBy` VARCHAR(45) NULL," + "`MadeYear` INT NULL,"
					+ "`Distance` INT NULL," + "`Price` INT NULL," + "`CampingCarDate` DATE NULL,"
					+ "`CampingcarPossible` INT NULL," + "`company_companyid` INT NOT NULL,"
					+ "PRIMARY KEY (`CampingcarId`),"
					+ "FOREIGN KEY(`company_companyid`) REFERENCES Company(`CompanyId`) ON DELETE CASCADE) ");
			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS `Rent`" + "(`RentId` INT NOT NULL,"
					+ "`StartDate` DATE NULL," + "`Period` INT NULL," + "`Charge` INT NULL," + "`ReturnDate` DATE NULL,"
					+ "`OtherCharge` VARCHAR(45) NULL," + "`ChargeInform` VARCHAR(45) NULL,"
					+ "`Campingcar_CampingcarId` INT NOT NULL," + "`client_ClientId` VARCHAR(40) NOT NULL,"
					+ "`company_CompanyId` INT NOT NULL," + "PRIMARY KEY (`RentId`),"
					+ "FOREIGN KEY(`company_companyid`) REFERENCES Company(`CompanyId`),"
					+ "FOREIGN KEY(`Campingcar_CampingcarId`) REFERENCES Campingcar(`CampingcarId`) ON DELETE CASCADE,"
					+ "FOREIGN KEY(`client_ClientId`) REFERENCES Client(`ClientId`) ON DELETE CASCADE)");

			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS `test`" + "(`TestId` INT NOT NULL,"
					+ "`Front` VARCHAR(45) NULL," + "`Left` VARCHAR(45) NULL," + "`Right` VARCHAR(45) NULL,"
					+ "`Back` VARCHAR(45) NULL," + "`FixNeed` VARCHAR(45) NULL,"
					+ "`campingcar_campingcarid` INT NOT NULL," + "`rent_rentid` INT NOT NULL,"
					+ "PRIMARY KEY (`TestId`),"
					+ "FOREIGN KEY(`Campingcar_CampingcarId`) REFERENCES Campingcar(`CampingcarId`) ON DELETE CASCADE,"
					+ "FOREIGN KEY(`rent_rentid`) REFERENCES Rent(`RentId`) ON DELETE CASCADE)");
			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS `Repairinfo`" + "(`RepairInfo` INT NOT NULL,"
					+ "`RepairContent` VARCHAR(45) NULL," + "`RepairDate` DATE NULL," + "`RepairCharge` INT NULL,"
					+ "`dueDate` DATE NULL," + "`OtherRepairInfo` VARCHAR(45) NULL,"
					+ "`company_companyid` INT NOT NULL," + "`client_clientid` VARCHAR(40) NOT NULL,"
					+ "`Campingcar_CampingcarId` INT NOT NULL," + "`RepairShop_RepairShopId` INT NOT NULL,"
					+ "PRIMARY KEY (`RepairInfo`),"
					+ "FOREIGN KEY(`company_companyid`) REFERENCES Company(`CompanyId`) ON DELETE CASCADE,"
					+ "FOREIGN KEY(`Campingcar_CampingcarId`) REFERENCES Campingcar(`CampingcarId`) ON DELETE CASCADE,"
					+ "FOREIGN KEY(`client_ClientId`) REFERENCES Client(`ClientId`) ON DELETE CASCADE,"
					+ "FOREIGN KEY(`RepairShop_RepairShopId`) REFERENCES RepairShop(`RepairShopId`) ON DELETE CASCADE)");
			stmt.executeUpdate("INSERT INTO company VALUES(1, '붕붕', '서울시 광진구', '010-8282-6969','허준현','hj1@naver.com')");
			stmt.executeUpdate(
					"INSERT INTO company VALUES(2, '칙칙', '서울시 용산구', '010-8282-1515','조성윤','cho1@naver.com')");
			stmt.executeUpdate(
					"INSERT INTO company VALUES(3, '폭폭', '서울시 성동구', '010-8282-1212','이신필','lsp1@naver.com')");
			stmt.executeUpdate(
					"INSERT INTO company VALUES(4, '빌려줘?', '서울시 동대문구', '010-8282-3434','강현수','khs1@naver.com')");
			stmt.executeUpdate(
					"INSERT INTO company VALUES(5, '말어?', '서울시 노원구', '010-8282-7878','이광렬','lgl1@naver.com')");
			stmt.executeUpdate(
					"INSERT INTO company VALUES(6, '꺠끗이', '서울시 도봉구', '010-8282-2222','방규빈','bgb@naver.com')");
			stmt.executeUpdate("INSERT INTO company VALUES(7, '쓰고', '서울시 마포구', '010-8282-3333','유정한','yjh@naver.com')");
			stmt.executeUpdate(
					"INSERT INTO company VALUES(8, '반납해', '서울시 송파구', '010-8282-4444','서정민','sjm@naver.com')");
			stmt.executeUpdate(
					"INSERT INTO company VALUES(9, 'company7', '서울시 강남구', '010-8282-5555','김형석','khs1@naver.com')");
			stmt.executeUpdate(
					"INSERT INTO company VALUES(10, 'company6', '서울시 서초구', '010-8282-6666','김영률','kyl1@naver.com')");
			stmt.executeUpdate(
					"INSERT INTO company VALUES(11, 'company5', '서울시 마구마구', '010-8282-8888','금하연','ghy@naver.com')");
			stmt.executeUpdate(
					"INSERT INTO company VALUES(12, 'company1', '서울시 야구', '010-8282-7777','이민영','lmy@naver.com')");
			stmt.executeUpdate(
					"INSERT INTO company VALUES(13, 'company2', '서울시 혼자구', '010-8282-9999','천혜원','chw@naver.com')");
			stmt.executeUpdate(
					"INSERT INTO company VALUES(14, 'company3', '서울시 솔로구', '010-8282-0000','임희원','lhw21@naver.com')");
			stmt.executeUpdate(
					"INSERT INTO company VALUES(15, 'company4', '서울시 아니구', '010-8282-1628','남기복','ngb2@naver.com')");

			stmt.executeUpdate(
					"INSERT INTO client VALUES('11-17-111111-11', '김방구', '서울시 광진구', '010-1111-1111','hj1@naver.com')");
			stmt.executeUpdate(
					"INSERT INTO client VALUES('12-17-111111-11', '김똥구', '서울시 롤하구', '010-2222-1111','hj11@naver.com')");
			stmt.executeUpdate(
					"INSERT INTO client VALUES('13-17-111111-11', '김공부', '서울시 동구', '010-3333-1111','hj21@naver.com')");
			stmt.executeUpdate(
					"INSERT INTO client VALUES('14-17-111111-11', '김하기', '서울시 마렵구', '010-4444-1111','hj31@naver.com')");
			stmt.executeUpdate(
					"INSERT INTO client VALUES('15-17-111111-11', '김싫다', '서울시 오지구', '010-5555-1111','hj41@naver.com')");
			stmt.executeUpdate(
					"INSERT INTO client VALUES('16-17-111111-11', '김놀러', '서울시 지리구', '010-6666-1111','hj51@naver.com')");
			stmt.executeUpdate(
					"INSERT INTO client VALUES('17-17-111111-11', '김가고', '서울시 렛잇구', '010-7777-1111','hj61@naver.com')");
			stmt.executeUpdate(
					"INSERT INTO client VALUES('18-17-111111-11', '김싶다', '서울시 아쉽구', '010-8888-1111','hj71@naver.com')");
			stmt.executeUpdate(
					"INSERT INTO client VALUES('19-17-111111-11', '김이름', '서울시 7광구', '010-9999-1111','hj81@naver.com')");
			stmt.executeUpdate(
					"INSERT INTO client VALUES('20-17-111111-11', '김짓기', '서울시 지렷구', '010-0000-1111','hj91@naver.com')");
			stmt.executeUpdate(
					"INSERT INTO client VALUES('21-17-111111-11', '김귀찮', '서울시 오졋구', '010-1212-1111','hj01@naver.com')");
			stmt.executeUpdate(
					"INSERT INTO client VALUES('22-17-111111-11', '김네', '서울시 구구구', '010-2323-1111','hj123@naver.com')");
			stmt.executeUpdate(
					"INSERT INTO client VALUES('23-17-111111-11', '김배가', '서울시 졸립구', '010-3434-1111','hj134@naver.com')");
			stmt.executeUpdate(
					"INSERT INTO client VALUES('24-17-111111-11', '김고파', '서울시 배구', '010-4545-1111','hj145@naver.com')");
			stmt.executeUpdate(
					"INSERT INTO client VALUES('25-17-111111-11', '김주거', '서울시 축구', '010-5656-1111','hj156@naver.com')");

			stmt.executeUpdate(
					"INSERT INTO repairshop VALUES(1, 'center1', '서울시 축구', '010-5744-1111','아리','abc156@naver.com')");
			stmt.executeUpdate(
					"INSERT INTO repairshop VALUES(2, 'center1', '서울시 야구', '010-5244-1111','브랜드','abc126@naver.com')");
			stmt.executeUpdate(
					"INSERT INTO repairshop VALUES(3, 'center1', '서울시 배구', '010-5344-1111','럭스','abc136@naver.com')");
			stmt.executeUpdate(
					"INSERT INTO repairshop VALUES(4, 'center1', '서울시 농구', '010-5444-1111','소나','abc146@naver.com')");
			stmt.executeUpdate(
					"INSERT INTO repairshop VALUES(5, 'center1', '서울시 강남구', '010-5844-1111','타릭','abc556@naver.com')");
			stmt.executeUpdate(
					"INSERT INTO repairshop VALUES(6, 'center1', '서울시 서초구', '010-5944-1111','노틸','abc656@naver.com')");
			stmt.executeUpdate(
					"INSERT INTO repairshop VALUES(7, 'center1', '서울시 광진구', '010-5044-1111','유미','abc856@naver.com')");
			stmt.executeUpdate(
					"INSERT INTO repairshop VALUES(8, 'center1', '서울시 마포구', '010-5434-1111','애쉬','abc2256@naver.com')");
			stmt.executeUpdate(
					"INSERT INTO repairshop VALUES(9, 'center1', '서울시 관악구', '010-5414-1111','케일','abc1126@naver.com')");
			stmt.executeUpdate(
					"INSERT INTO repairshop VALUES(10, 'center1', '서울시 강동구', '010-5244-1111','카르마','abc1246@naver.com')");
			stmt.executeUpdate(
					"INSERT INTO repairshop VALUES(11, 'center1', '서울시 동작구', '010-5404-1111','이렐','abc1555@naver.com')");
			stmt.executeUpdate(
					"INSERT INTO repairshop VALUES(12, 'center1', '서울시 노원구', '010-5484-1111','리븐','abc1776@naver.com')");
			stmt.executeUpdate(
					"INSERT INTO repairshop VALUES(13, 'center1', '서울시 성동구', '010-5444-1211','자이라','abc1116@naver.com')");
			stmt.executeUpdate(
					"INSERT INTO repairshop VALUES(14, 'center1', '서울시 용산구', '010-5444-1311','이즈','abc12356@naver.com')");
			stmt.executeUpdate(
					"INSERT INTO repairshop VALUES(15, 'center1', '서울시 서대문구', '010-5444-4111','바루스','abc15d6@naver.com')");

			stmt.executeUpdate(
					"INSERT INTO campingcar VALUES(1, '벤츠', '11가 1234', 5,'기아',2020,5000,40000,STR_TO_DATE('2014-07-01','%Y-%m-%d'),1,1)");
			stmt.executeUpdate(
					"INSERT INTO campingcar VALUES(2, '포르쉐', '12가 1234', 6,'기아',2019,6000,40000,STR_TO_DATE('2015-07-01','%Y-%m-%d'),1,2)");
			stmt.executeUpdate(
					"INSERT INTO campingcar VALUES(3, '람보르기니', '13가 1234', 5,'기아',2019,7000,50000,STR_TO_DATE('2016-07-01','%Y-%m-%d'),1,3)");
			stmt.executeUpdate(
					"INSERT INTO campingcar VALUES(4, '티코', '14가 1234', 10,'기아',2019,8000,60000,STR_TO_DATE('2017-07-01','%Y-%m-%d'),1,4)");
			stmt.executeUpdate(
					"INSERT INTO campingcar VALUES(5, 'BMW', '15가 1234', 11,'기아',2018,9000,70000,STR_TO_DATE('2018-07-01','%Y-%m-%d'),1,2)");
			stmt.executeUpdate(
					"INSERT INTO campingcar VALUES(6, '소나타', '16가 1234', 4,'기아',2018,10000,80000,STR_TO_DATE('2017-07-01','%Y-%m-%d'),1,7)");
			stmt.executeUpdate(
					"INSERT INTO campingcar VALUES(7, '제네시스', '17가 1234', 9,'기아',2017,11000,90000,STR_TO_DATE('2016-07-01','%Y-%m-%d'),1,8)");
			stmt.executeUpdate(
					"INSERT INTO campingcar VALUES(8, '렉서스', '18가 1234', 7,'기아',2017,12000,100000,STR_TO_DATE('2012-07-01','%Y-%m-%d'),1,13)");
			stmt.executeUpdate(
					"INSERT INTO campingcar VALUES(9, '아우디', '19가 1234', 5,'기아',2016,13000,110000,STR_TO_DATE('2011-07-01','%Y-%m-%d'),1,12)");
			stmt.executeUpdate(
					"INSERT INTO campingcar VALUES(10, '자전거', '10가 1234', 5,'기아',2016,14000,120000,STR_TO_DATE('2019-07-01','%Y-%m-%d'),1,11)");
			stmt.executeUpdate(
					"INSERT INTO campingcar VALUES(11, '폐차', '21가 1234', 5,'기아',2020,15000,130000,STR_TO_DATE('2020-07-01','%Y-%m-%d'),1,2)");
			stmt.executeUpdate(
					"INSERT INTO campingcar VALUES(12, '비행기', '22가 1234', 3,'기아',2020,16000,140000,STR_TO_DATE('2011-07-01','%Y-%m-%d'),1,9)");
			stmt.executeUpdate(
					"INSERT INTO campingcar VALUES(13, 'ktx', '23가 1234', 4,'기아',2015,17000,150000,STR_TO_DATE('2010-07-01','%Y-%m-%d'),1,10)");
			stmt.executeUpdate(
					"INSERT INTO campingcar VALUES(14, '걷기', '24가 1234', 8,'기아',2015,18000,160000,STR_TO_DATE('2009-07-01','%Y-%m-%d'),1,9)");
			stmt.executeUpdate(
					"INSERT INTO campingcar VALUES(15, '아시아나', '25가 1234', 9,'기아',2014,19000,140000,STR_TO_DATE('2011-07-01','%Y-%m-%d'),1,6)");

			stmt.executeUpdate(
					"INSERT INTO repairinfo VALUES(1,'바퀴고침',STR_TO_DATE('2020-03-10','%Y-%m-%d'),200000,STR_TO_DATE('2020-03-14','%Y-%m-%d'),'휠교체-30000원',1,'11-17-111111-11',1,2)");
			stmt.executeUpdate(
					"INSERT INTO repairinfo VALUES(2,'범퍼고침',STR_TO_DATE('2019-09-10','%Y-%m-%d'),300000,STR_TO_DATE('2019-09-14','%Y-%m-%d'),'범퍼교체-130000원',1,'11-17-111111-11',1,2)");
			stmt.executeUpdate(
					"INSERT INTO repairinfo VALUES(3,'바퀴고침',STR_TO_DATE('2019-09-22','%Y-%m-%d'),200000,STR_TO_DATE('2019-09-25','%Y-%m-%d'),'핸들교체-120000원',3,'13-17-111111-11',3,2)");
			stmt.executeUpdate(
					"INSERT INTO repairinfo VALUES(4,'라이트고침',STR_TO_DATE('2020-06-19','%Y-%m-%d'),400000,STR_TO_DATE('2020-06-20','%Y-%m-%d'),'배기통교체-110000원',4,'13-17-111111-11',4,3)");
			stmt.executeUpdate(
					"INSERT INTO repairinfo VALUES(5,'범퍼고침',STR_TO_DATE('2020-04-15','%Y-%m-%d'),100000,STR_TO_DATE('2020-04-22','%Y-%m-%d'),'에어컨교체-230000원',4,'15-17-111111-11',4,4)");
			stmt.executeUpdate(
					"INSERT INTO repairinfo VALUES(6,'깜박이고침',STR_TO_DATE('2020-01-10','%Y-%m-%d'),150000,STR_TO_DATE('2020-01-16','%Y-%m-%d'),'안전벨트교체-220000원',4,'16-17-111111-11',4,1)");
			stmt.executeUpdate(
					"INSERT INTO repairinfo VALUES(7,'차틀고침',STR_TO_DATE('2020-02-10','%Y-%m-%d'),120000,STR_TO_DATE('2020-02-19','%Y-%m-%d'),'차체업그레이드-330000원',6,'17-17-111111-11',15,1)");
			stmt.executeUpdate(
					"INSERT INTO repairinfo VALUES(8,'창문고침',STR_TO_DATE('2020-03-14','%Y-%m-%d'),340000,STR_TO_DATE('2020-03-16','%Y-%m-%d'),'의자교체-250000원',9,'18-17-111111-11',12,5)");
			stmt.executeUpdate(
					"INSERT INTO repairinfo VALUES(9,'창문고침',STR_TO_DATE('2020-03-15','%Y-%m-%d'),220000,STR_TO_DATE('2020-03-24','%Y-%m-%d'),'운전자교체-20000원',9,'19-17-111111-11',12,5)");
			stmt.executeUpdate(
					"INSERT INTO repairinfo VALUES(10,'바퀴고침',STR_TO_DATE('2020-03-16','%Y-%m-%d'),410000,STR_TO_DATE('2020-03-26','%Y-%m-%d'),'선수교체-430000원',9,'12-17-111111-11',12,6)");
			stmt.executeUpdate(
					"INSERT INTO repairinfo VALUES(11,'범퍼고침',STR_TO_DATE('2020-03-19','%Y-%m-%d'),100000,STR_TO_DATE('2020-03-28','%Y-%m-%d'),'블랙박스교체-100000원',11,'21-17-111111-11',10,14)");
			stmt.executeUpdate(
					"INSERT INTO repairinfo VALUES(12,'깜박이고침',STR_TO_DATE('2020-04-12','%Y-%m-%d'),110000,STR_TO_DATE('2020-04-20','%Y-%m-%d'),'일단교체-5000원',11,'21-17-111111-11',10,13)");
			stmt.executeUpdate(
					"INSERT INTO repairinfo VALUES(13,'창문고침',STR_TO_DATE('2020-04-11','%Y-%m-%d'),90000,STR_TO_DATE('2020-04-19','%Y-%m-%d'),'깜박이교체-34000원',13,'23-17-111111-11',8,1)");
			stmt.executeUpdate(
					"INSERT INTO repairinfo VALUES(14,'바퀴고침',STR_TO_DATE('2020-05-13','%Y-%m-%d'),800000,STR_TO_DATE('2020-05-23','%Y-%m-%d'),'휠교체-30000원',2,'24-17-111111-11',5,7)");
			stmt.executeUpdate(
					"INSERT INTO repairinfo VALUES(15,'와이퍼고침',STR_TO_DATE('2020-05-12','%Y-%m-%d'),210000,STR_TO_DATE('2020-05-22','%Y-%m-%d'),'라이트교체-220000원',2,'25-17-111111-11',5,8)");

			stmt.executeUpdate(
					"INSERT INTO rent VALUES(1, STR_TO_DATE('2020-03-10','%Y-%m-%d'), 15, 300000, STR_TO_DATE('2020-04-10','%Y-%m-%d'), '주차비',19000,1,'11-17-111111-11',1)");
			stmt.executeUpdate(
					"INSERT INTO rent VALUES(2, STR_TO_DATE('2020-02-11','%Y-%m-%d'), 3, 100000, STR_TO_DATE('2020-03-11','%Y-%m-%d'), '인건비',12000,1,'11-17-111111-11',1)");
			stmt.executeUpdate(
					"INSERT INTO rent VALUES(3, STR_TO_DATE('2020-03-12','%Y-%m-%d'), 12, 200000, STR_TO_DATE('2020-03-15','%Y-%m-%d'), '치료비',13000,3,'11-17-111111-11',3)");
			stmt.executeUpdate(
					"INSERT INTO rent VALUES(4, STR_TO_DATE('2020-03-13','%Y-%m-%d'), 14, 300000, STR_TO_DATE('2020-04-10','%Y-%m-%d'), '가수비',14000,3,'11-17-111111-11',3)");
			stmt.executeUpdate(
					"INSERT INTO rent VALUES(5, STR_TO_DATE('2020-03-14','%Y-%m-%d'), 5, 400000, STR_TO_DATE('2020-04-10','%Y-%m-%d'), '이슬비',190000,4,'11-17-111111-11',4)");
			stmt.executeUpdate(
					"INSERT INTO rent VALUES(6, STR_TO_DATE('2020-03-15','%Y-%m-%d'), 5, 500000, STR_TO_DATE('2020-04-10','%Y-%m-%d'), '유지비',12000,1,'11-17-111111-11',1)");
			stmt.executeUpdate(
					"INSERT INTO rent VALUES(7, STR_TO_DATE('2020-03-16','%Y-%m-%d'), 6, 400000, STR_TO_DATE('2020-04-10','%Y-%m-%d'), '보수비',21000,3,'11-17-111111-11',3)");
			stmt.executeUpdate(
					"INSERT INTO rent VALUES(8, STR_TO_DATE('2020-03-17','%Y-%m-%d'), 7, 300000, STR_TO_DATE('2020-04-10','%Y-%m-%d'), '차비',39000,7,'11-17-111111-11',8)");
			stmt.executeUpdate(
					"INSERT INTO rent VALUES(9, STR_TO_DATE('2020-03-18','%Y-%m-%d'), 9, 200000, STR_TO_DATE('2020-04-10','%Y-%m-%d'), '택시비',14000,7,'11-17-111111-11',8)");
			stmt.executeUpdate(
					"INSERT INTO rent VALUES(10, STR_TO_DATE('2020-03-19','%Y-%m-%d'), 2, 300000, STR_TO_DATE('2020-04-10','%Y-%m-%d'), '버스비',39000,1,'11-17-111111-11',1)");
			stmt.executeUpdate(
					"INSERT INTO rent VALUES(11, STR_TO_DATE('2020-03-20','%Y-%m-%d'), 11, 300000, STR_TO_DATE('2020-04-10','%Y-%m-%d'), '주차비',29000,15,'11-17-111111-11',6)");
			stmt.executeUpdate(
					"INSERT INTO rent VALUES(12, STR_TO_DATE('2020-04-12','%Y-%m-%d'), 8, 400000, STR_TO_DATE('2020-05-10','%Y-%m-%d'), '고비',9000,6,'11-17-111111-11',7)");
			stmt.executeUpdate(
					"INSERT INTO rent VALUES(13, STR_TO_DATE('2020-04-11','%Y-%m-%d'), 9, 200000, STR_TO_DATE('2020-05-10','%Y-%m-%d'), '자린고비',2000,14,'11-17-111111-11',9)");
			stmt.executeUpdate(
					"INSERT INTO rent VALUES(14, STR_TO_DATE('2020-04-18','%Y-%m-%d'), 7, 140000, STR_TO_DATE('2020-05-10','%Y-%m-%d'), '채비',4000,14,'11-17-111111-11',9)");
			stmt.executeUpdate(
					"INSERT INTO rent VALUES(15, STR_TO_DATE('2020-04-15','%Y-%m-%d'), 7, 150000, STR_TO_DATE('2020-05-10','%Y-%m-%d'), '유비',5000,15,'11-17-111111-11',6)");

			stmt.executeUpdate("INSERT INTO test VALUES(1,'별 일 없음','별 일 없음','별 일 없음','별 일 없음','no',1,1)");
			stmt.executeUpdate("INSERT INTO test VALUES(2,'기스남','별 일 없음','별 일 없음','별 일 없음','yes',1,2)");
			stmt.executeUpdate("INSERT INTO test VALUES(3,'범퍼고장','별 일 없음','별 일 없음','별 일 없음','yes',3,3)");
			stmt.executeUpdate("INSERT INTO test VALUES(4,'별 일 없음','별 일 없음','별 일 없음','별 일 없음','no',3,4)");
			stmt.executeUpdate("INSERT INTO test VALUES(5,'기스남','별 일 없음','별 일 없음','별 일 없음','yes',4,5)");
			stmt.executeUpdate("INSERT INTO test VALUES(6,'별 일 없음','기스남','별 일 없음','별 일 없음','yes',1,6)");
			stmt.executeUpdate("INSERT INTO test VALUES(7,'별 일 없음','별 일 없음','별 일 없음','별 일 없음','no',3,7)");
			stmt.executeUpdate("INSERT INTO test VALUES(8,'별 일 없음','사이드미러 부숴짐','별 일 없음','별 일 없음','yes',7,8)");
			stmt.executeUpdate("INSERT INTO test VALUES(9,'별 일 없음','별 일 없음','사이드미러 부숴짐','별 일 없음','yes',7,9)");
			stmt.executeUpdate("INSERT INTO test VALUES(10,'별 일 없음','별 일 없음','사이드미러 부숴짐','별 일 없음','yes',1,10)");
			stmt.executeUpdate("INSERT INTO test VALUES(11,'별 일 없음','별 일 없음','별 일 없음','벞퍼긁힘','yes',15,11)");
			stmt.executeUpdate("INSERT INTO test VALUES(12,'별 일 없음','별 일 없음','별 일 없음','별 일 없음','no',6,12)");
			stmt.executeUpdate("INSERT INTO test VALUES(13,'별 일 없음','별 일 없음','기스남','별 일 없음','yes',14,13)");
			stmt.executeUpdate("INSERT INTO test VALUES(14,'별 일 없음','별 일 없음','별 일 없음','별 일 없음','no',14,14)");
			stmt.executeUpdate("INSERT INTO test VALUES(15,'별 일 없음','별 일 없음','별 일 없음','별 일 없음','no',15,15)");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/*-------------------TABLE이 선택되었는지 확인 메소드-------------------------*/
	public boolean checkSelected(JTable table) {
		boolean selected = false;
		for (int i = 0; i < table.getRowCount(); i++) {
			if (table.isRowSelected(i))
				selected = true;
		}
		return selected;
	}

	/*--------------------정수 검사 메소드--------------------------------*/
	public boolean isInt(String tmp) {
		boolean result = true;
		for (int i = 0; i < tmp.length(); i++) {
			if (tmp.charAt(i) < '0' || tmp.charAt(i) > '9') {
				return false;
			}
		}
		return true;
	}

	/*-----------------------NULL값 체크 메소드-------------------------------*/
	public boolean isNull(String str) {
		boolean result = false;
		if (str.equals(""))
			result = true;
		return result;
	}

	/*-------------------무결성 검사 메소드---------------------------*/
	public boolean checkIntegrity(String table, String id, String value) {
		boolean result = false;
		String sql = "select * from " + table + " where " + id + " =?";
		PreparedStatement pst = null;
		try {
			pst = con.prepareStatement(sql);
			pst.setString(1, value);
			pst.executeQuery();
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				result = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

			try {
				if (pst != null && !pst.isClosed())
					pst.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	/*------------------DATE형식인지 확인하는 메소드-----------------------*/
	public boolean validationDate(String checkDate) {

		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

			dateFormat.setLenient(false);
			dateFormat.parse(checkDate);
			return true;

		} catch (ParseException e) {
			return false;
		}

	}

	/* 메인 메소드 */
	public static void main(String[] args) {
		DBProject BLS = new DBProject();

		BLS.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				try {
					con.close();
				} catch (Exception e4) {
				}
				System.out.println("프로그램 완전 종료!");
				System.exit(0);
			}
		});
	}

}
