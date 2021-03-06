package com.github.etkachev.nxwebstorm.actionlisteners

import com.github.etkachev.nxwebstorm.models.SchematicInfo
import com.github.etkachev.nxwebstorm.ui.SchematicsListDialog
import com.github.etkachev.nxwebstorm.utils.findFullSchematicIdByTypeAndId
import com.intellij.ui.table.JBTable
import javax.swing.event.ListSelectionEvent
import javax.swing.event.ListSelectionListener

class SchematicActionListener(
  private val table: JBTable,
  private val schematics: Map<String, SchematicInfo>,
  private val panel: SchematicsListDialog
) : ListSelectionListener {
  override fun valueChanged(e: ListSelectionEvent?) {
    if (e != null && e.valueIsAdjusting) {
      return
    }
    if (table.selectedRow == -1) {
      return
    }
    val selectedRow = table.selectedRow
    val type = table.getValueAt(selectedRow, 0).toString()
    val id = table.getValueAt(selectedRow, 1).toString()
    val fullId = findFullSchematicIdByTypeAndId(type, id, schematics) ?: return
    val info = schematics[fullId] ?: return
    panel.schematicSelection["id"] = id
    panel.schematicSelection["type"] = type
    panel.schematicSelection["file"] = info.fileLocation
  }
}
