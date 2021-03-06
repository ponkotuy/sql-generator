package com.geishatokyo.sqlgen.process.merge

import com.geishatokyo.sqlgen.process.{ Proc, ProcessProvider}
import com.geishatokyo.sqlgen.sheet.{CellUnit, Sheet, Workbook}
import com.geishatokyo.sqlgen.util.I18NUtil
import com.geishatokyo.sqlgen.process.input.SingleXLSLoader
import com.geishatokyo.sqlgen.project.BaseProject

/**
 *
 * User: takeshita
 * Create: 12/07/13 19:33
 */

trait WorkbookMergeProcessProvider extends ProcessProvider {

  type ProjectType <: BaseProject

  def load(filename : String) = {
    SingleXLSLoader.apply(filename).load(project,context)
  }

  def mergeWorkbookProc( workbook : Workbook) : Proc = {
    new WorkbookMergeProcess(workbook)
  }
  def mergeWorkbookProc( filename : String) : Proc = {
    new WorkbookMergeProcess(load(filename))
  }


  class WorkbookMergeProcess( merge : Workbook) extends Proc{
    def name: String = "MergeWorkbook"

    def apply(base: Workbook ): Workbook = {

      base.foreachSheet(sheet => {
        merge.getSheet(sheet.name) match{
          case Some(_sheet) => {
            logger.log("Merge %s".format(sheet.name))
            mergeSheet(sheet,_sheet)
          }
          case None =>
        }
      })
      base

    }

    def mergeSheet(base : Sheet , merge : Sheet) = {

      base.foreachRow(row => {
        val ids = base.ids.map(h => row(h.name).value)
        merge.findFirstRowWhere( searchRow => {
          val mergeRowId = base.ids.map(h => searchRow(h.name).value)
          ids == mergeRowId
        }) match{
          case Some( toMerge) => {
            toMerge.units.foreach({
              case CellUnit(h,c) => {
                row(h.name) := c.value
              }
            })
          }
          case None =>
        }
      })


    }
  }

}
