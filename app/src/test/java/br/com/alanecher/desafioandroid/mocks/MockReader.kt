package br.com.alanecher.desafioandroid.mocks

import br.com.alanecher.desafioandroid.domain.Character
import com.google.gson.Gson
import junit.framework.Assert
import java.io.*

class MockReader<T> {
    fun getMock(fileName:String, classOfT:Class<T>): T {
        val path = "src/test/resources"

        val file = File(path)
        val absolutePath: String = file.absolutePath

        Assert.assertTrue(absolutePath.endsWith("src/test/resources"))

        return Gson().fromJson(getStringFromFile("${file.absolutePath}/${fileName}.json"),classOfT) as T
    }

    @Throws(Exception::class)
    fun getStringFromFile(filePath: String?): String? {
        val fl = File(filePath)
        val fin = FileInputStream(fl)
        val ret: String = convertStreamToString(fin)
        fin.close()
        return ret
    }

    @Throws(java.lang.Exception::class)
    fun convertStreamToString(`is`: InputStream?): String {
        val reader = BufferedReader(InputStreamReader(`is`))
        val sb = StringBuilder()
        var line: String? = null
        while (reader.readLine().also { line = it } != null) {
            sb.append(line)
        }
        reader.close()
        return sb.toString()
    }
}