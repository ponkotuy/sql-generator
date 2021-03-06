package com.geishatokyo.sqlgen

import project2.input.{WorkbookInput, XLSFileInput, AllXlsFileInDirInput}
import com.geishatokyo.sqlgen.project2.output.{SQLiteOutput, MySQLOutput, XlsOutput, ConsoleOutput}
import sheet.Workbook

/**
 *
 * User: takeshita
 * DateTime: 13/07/12 2:15
 */
package object project2 {

  val ColumnType = com.geishatokyo.sqlgen.sheet.ColumnType

  def withWorkbook(wb : Workbook) = {
    new WorkbookInput(wb)
  }

  def inDir(dir : String) : Input = {
    new AllXlsFileInDirInput(dir)
  }

  def file(file : String) : Input = {
    new XLSFileInput(file)
  }


  def console = {
    new ConsoleOutput()
  }

  def asXls : Output = {
    new XlsOutput( s => s)
  }

  def asXls( conversion : String => String) : Output = {
    new XlsOutput(conversion)
  }

  def asSql : Output = {
    new MySQLOutput(s => s)
  }

  def asSql(filenameConversion : String => String) : Output = {
    new MySQLOutput(filenameConversion)
  }

  def asSqlite : Output = {
    new SQLiteOutput(s => s)
  }
  def asSqlite(filenameConversion : String => String) : Output = {
    new SQLiteOutput(filenameConversion)
  }

}



