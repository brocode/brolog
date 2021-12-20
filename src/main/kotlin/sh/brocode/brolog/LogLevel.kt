package sh.brocode.brolog

enum class LogLevel(
    val traceEnabled: Boolean = false,
    val debugEnabled: Boolean = false,
    val infoEnabled: Boolean = false,
    val warnEnabled: Boolean = false,
    val errorEnabled: Boolean = false,
) {
    TRACE(
        traceEnabled = true,
        debugEnabled = true,
        infoEnabled = true,
        warnEnabled = true,
        errorEnabled = true,
    ),
    DEBUG(
        debugEnabled = true,
        infoEnabled = true,
        warnEnabled = true,
        errorEnabled = true,
    ),
    INFO(
        infoEnabled = true,
        warnEnabled = true,
        errorEnabled = true,
    ),
    WARN(
        warnEnabled = true,
        errorEnabled = true,
    ),
    ERROR(
        errorEnabled = true,
    ),
}
