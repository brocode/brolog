package sh.brocode.brolog

enum class LogLevel(
    val traceEnabled: Boolean,
    val debugEnabled: Boolean,
    val infoEnabled: Boolean,
    val warnEnabled: Boolean,
    val errorEnabled: Boolean,
) {
    TRACE(
        traceEnabled = true,
        debugEnabled = true,
        infoEnabled = true,
        warnEnabled = true,
        errorEnabled = true,
    ),
    DEBUG(
        traceEnabled = false,
        debugEnabled = true,
        infoEnabled = true,
        warnEnabled = true,
        errorEnabled = true,
    ),
    INFO(
        traceEnabled = false,
        debugEnabled = false,
        infoEnabled = true,
        warnEnabled = true,
        errorEnabled = true,
    ),
    WARN(
        traceEnabled = false,
        debugEnabled = false,
        infoEnabled = false,
        warnEnabled = true,
        errorEnabled = true,
    ),
    ERROR(
        traceEnabled = false,
        debugEnabled = false,
        infoEnabled = false,
        warnEnabled = false,
        errorEnabled = true,
    ),
}
