package fr.nicopico.nirvanawear

class TestResource(
    private val path: String
) {
    val content: String?
        get() = this::class.java.classLoader?.getResource(path)?.readText()
}
