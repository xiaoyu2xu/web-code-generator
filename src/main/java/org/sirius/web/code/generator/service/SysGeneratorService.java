package org.sirius.web.code.generator.service;

import org.apache.commons.io.IOUtils;
import org.sirius.web.code.generator.dao.SQLServerGeneratorDao;
import org.sirius.web.code.generator.dao.SysGeneratorDao;
import org.sirius.web.code.generator.utils.GenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipOutputStream;

/**
 * 代码生成器
 */
@Service
public class SysGeneratorService {
//	@Autowired
//	private SysGeneratorDao sysGeneratorDao;

	@Autowired
	private SQLServerGeneratorDao sqlServerGeneratorDao;

	public List<Map<String, Object>> queryList(Map<String, Object> map) {
		return sqlServerGeneratorDao.queryList(map);
	}

//	public int queryTotal(Map<String, Object> map) {
//		return sqlServerGeneratorDao.queryTotal(map);
//	}

	public Map<String, String> queryTable(String tableName) {
		return sqlServerGeneratorDao.queryTable(tableName);
	}

	public List<Map<String, String>> queryColumns(String tableName) {
		return sqlServerGeneratorDao.queryColumns(tableName);
	}

	public byte[] generatorCode(String[] tableNames) {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		ZipOutputStream zip = new ZipOutputStream(outputStream);

		for(String tableName : tableNames){
			//查询表信息
			Map<String, String> table = queryTable(tableName);
			//查询列信息
			List<Map<String, String>> columns = queryColumns(tableName);
			//生成代码
			GenUtils.generatorCode(table, columns, zip);
		}
		IOUtils.closeQuietly(zip);
		return outputStream.toByteArray();
	}
}
