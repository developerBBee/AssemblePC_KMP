package jp.developer.bbee.assemblepc.domain.model

import com.google.common.truth.Truth
import jp.developer.bbee.assemblepc.common.defaultJson
import kotlinx.serialization.Serializable
import org.junit.Test

class PriceTest {

    @Test
    fun serializeTest() {
        val testData = TestData(price = 1234.toPrice())
        val actualJson = defaultJson.encodeToString(testData)
        val expectedJson = """{"price":1234}"""
        println(actualJson)
        Truth.assertThat(actualJson).isEqualTo(expectedJson)
    }

    @Test
    fun deserializeTest() {
        val testJson = """{"price":567890}"""
        val actualData = defaultJson.decodeFromString<TestData>(testJson)
        val expected = TestData(price = 567890.toPrice())
        println(actualData)
        Truth.assertThat(actualData).isEqualTo(expected)
    }
}

@Serializable
private data class TestData(
    val price: Price
)
