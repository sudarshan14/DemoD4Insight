package sud.bhatt

import org.jetbrains.annotations.TestOnly

class AndroidInterview private constructor(builder: Builder) {
    var lowDifficulty: String? = null
    var mediumDifficulty: String? = null
    var highDifficulty: String? = null

    class Builder {

        private var low: String? = null
        private var medium: String? = null
        private var high: String? = null

        fun setLowDifficulty(param: String) = apply {
            this.low = param
        }

        fun setMediumDifficulty(param: String) = apply {
            this.medium = param
        }

        fun setHighDifficulty(param: String) = apply {
            this.high = param
        }

        fun build() = AndroidInterview(this)

        fun getLow() = low
        fun getMedium() = medium
        fun getHigh() = high

    }

    init {
        lowDifficulty = builder.getLow()
        mediumDifficulty = builder.getMedium()
        highDifficulty = builder.getHigh()
    }
}

