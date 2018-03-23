package region

import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import org.apache.commons.csv.CSVRecord
import java.io.File
import java.nio.charset.Charset

enum class Encounter {
    GRASS,
    SURF,
    OLD_ROD,
    GOOD_ROD,
    SUPER_ROD
}

enum class Column {
    NAME,
    MIN_LEVEL,
    MAX_LEVEL,
    RATE_DAWN,
    RATE_DAY,
    RATE_DUSK
}


class WildlifeParser(file: File?) {

    private val records: List<CSVRecord>

    init {
        records = if ( file != null ) {
            val parser = CSVParser.parse(file, Charset.defaultCharset(), CSVFormat.EXCEL)
            parser.records
        } else {
            emptyList()
        }
    }
}