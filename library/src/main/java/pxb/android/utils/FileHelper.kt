package pxb.android.utils

import java.io.*
import java.nio.charset.StandardCharsets

object FileHelper {
    @JvmStatic
    fun readFile(file: File): ByteArray {
        return file.readBytes()
    }

    @JvmStatic
    fun writeToFile(out: File, data: ByteArray) = out.writeBytes(data)

    @Throws(IOException::class)
    fun readProguardConfig(config: File): Map<String, String> {
        val clzMap = HashMap<String, String>()
        BufferedReader(InputStreamReader(FileInputStream(config), StandardCharsets.UTF_8)).use { r ->
            var ln = r.readLine()
            while (ln != null) {
                if (ln.startsWith("#") || ln.startsWith(" ")) {
                    ln = r.readLine()
                    continue
                }
                // format a.pt.Main -> a.a.a:
                val i = ln.indexOf("->")
                if (i > 0) {
                    clzMap[ln.substring(0, i).trim { it <= ' ' }] =
                        ln.substring(i + 2, ln.length - 1).trim { it <= ' ' }
                }
                ln = r.readLine()
            }
        }
        return clzMap
    }

    @Throws(IOException::class)
    fun copy(inputStream: InputStream, os: OutputStream) {
        inputStream.copyTo(os)
    }
}
