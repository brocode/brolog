package sh.brocode.brolog

import kotlinx.serialization.Serializable

@Serializable
data class LogEntry(
    val time: String,
    val logger: String,
    val message: String,
    val mdc: Map<String, String?>?,
    val marker: Set<String>?,
    val level: LogLevel,
    val exception: String?,
    val keyValues: Map<String, String>? = null,
)
