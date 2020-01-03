package ng.com.techdepo.kotlincodelabs


//API Base URL
const val BASE_URL = " https://android-kotlin-fun-mars-server.appspot.com/"

// API Filter Keys
enum class MarsApiFilter(val value: String) {
    SHOW_RENT("rent"),
    SHOW_BUY("buy"),
    SHOW_ALL("all") }