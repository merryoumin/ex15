package model.memo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;

import db.DbExample;
import model.memo.dto.MemoDTO;


public class MemoDAO {
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	public Connection getConn() {
		conn = DbExample.dbConn();
		return conn;
	}
	
	public void getConnClose(ResultSet rs, PreparedStatement pstmt, Connection conn) {
		DbExample.dbConnClose(rs, pstmt, conn);
	}
	
	public int setInsert(MemoDTO dto) {
		int result = 0;
		conn = getConn();
		
		try {
			String sql = "insert into memo values(seq_memo.nextVal, "
						+ "?, ?,"
						+ "sysdate)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getName());
			pstmt.setString(2, dto.getMemo());

			
			result = pstmt.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			getConnClose(rs, pstmt, conn);
		}
		
		return result;
	}
	
	public int getTotalRecord() {
		getConn();
		int result = 0;
		try {
			String sql = "select count(*) from memo";
			pstmt = conn.prepareStatement(sql);	
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				result = rs.getInt(1);			
			}
			
			System.out.println("--getTotalRecord--: "+result);
		
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			getConnClose(rs, pstmt, conn);
		}
		
		return result;
	}
	
	
	
	public ArrayList<MemoDTO> getListAll(int startRecord,int lastRecord) {
		ArrayList<MemoDTO> list = new ArrayList<>();
		getConn();
		
		try {
			String basic_sql = "";
			       basic_sql += "select * from memo order by id desc";	
			String sql = ""
						+ " SELECT * FROM (SELECT a.*, ROWNUM Rnum FROM "
				   		+ "("+basic_sql+") a)"
				   		+ "WHERE Rnum >=? and Rnum <=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, startRecord);
			pstmt.setInt(2, lastRecord);
			
			rs = pstmt.executeQuery();
//			System.out.println("----------list dao insert-----------");
//			System.out.println("startRecord : "+startRecord);
//			System.out.println("lastRecord : "+ lastRecord);
		
			while(rs.next()) {	
				MemoDTO dto = new MemoDTO();
	            dto.setId(rs.getInt("id"));
	            dto.setName(rs.getString("name"));
	            dto.setMemo(rs.getString("memo"));
	            dto.setRegiDate(rs.getTimestamp("regiDate"));
				list.add(dto);
				
				System.out.println("----getListAll dao-----");
				System.out.println("indbid : "+dto.getId());
				System.out.println("indbname : "+dto.getName());
				System.out.println("indbmemo : "+dto.getMemo());
				System.out.println("indbregiDate : "+dto.getRegiDate());
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			getConnClose(rs, pstmt, conn);
		}
		
		return list;
		
	}
	

	
	
}
