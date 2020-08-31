package com.github.etkachev.nxwebstorm.actions

import com.github.etkachev.nxwebstorm.ui.RunSchematicDialog
import com.github.etkachev.nxwebstorm.ui.RunTerminalWindow
import com.github.etkachev.nxwebstorm.ui.SchematicsListDialog
import com.github.etkachev.nxwebstorm.utils.GetNxData
import com.intellij.execution.filters.TextConsoleBuilderFactory
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.wm.ToolWindowManager

class Generate : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        if (e.project == null) {
            return
        }

        val proj = e.project!!
        val schematics = GetNxData().getCustomSchematics(proj)

        val dialog = SchematicsListDialog(proj, schematics)
        dialog.setSize(800, 100)
        val ok = dialog.showAndGet()
        if (ok && dialog.schematicSelection.containsKey("id")) {
            val id = dialog.schematicSelection["id"] ?: ""
            val fileLocation = dialog.schematicSelection["file"] ?: ""
            val formDialog = RunSchematicDialog(proj, id, fileLocation)
            formDialog.setSize(800, 600)
            val formOk = formDialog.showAndGet()
            if (formOk) {
                val values = formDialog.formMap.formVal
                val terminal = RunTerminalWindow(proj)
                terminal.runAndShow("ls")
            }
        }
    }
}