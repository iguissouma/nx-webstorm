package com.github.etkachev.nxwebstorm.utils

fun getSchematicCommandFromValues(
  type: String,
  id: String,
  values: MutableMap<String, String>,
  dryRun: Boolean = true
): String {
  val keys = values.keys
  val dryRunString = if (dryRun) " --dry-run" else ""
  val flagCommands = keys.mapNotNull { key ->
    val value = values[key]
    val finalText = if (value == "true" || value == "false") {
      if (value == "true") "--$key" else null
    } else if (value != null && value.isNotBlank()) {
      val cleanedValue = if (value.contains(" ")) "'$value'" else value
      "--$key=$cleanedValue"
    } else {
      null
    }
    finalText
  }
  val nx = "node node_modules/@nrwl/cli/bin/nx.js"
  val prefix = if (type == "workspace-schematic") "$nx workspace-schematic $id" else "$nx generate $type:$id"
  val flags = flagCommands.joinToString(" ")
  return "$prefix $flags --no-interactive$dryRunString"
}
