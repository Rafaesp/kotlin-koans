package iii_conventions

data class MyDate(val year: Int, val month: Int, val dayOfMonth: Int) {
    operator fun compareTo(date: MyDate): Int {
        var comp = this.year.compareTo(date.year)
        if (comp != 0) {
            return comp;
        }
        comp = this.month.compareTo(date.month)
        if (comp != 0) {
            return comp;
        }
        comp = this.dayOfMonth.compareTo(date.dayOfMonth)
        return comp;
    }

    operator fun plus(t: TimeInterval): MyDate {
        return this.addTimeIntervals(t, 1)
    }

    operator fun plus(repeatedTimeInterval: RepeatedTimeInterval): MyDate {
        return this.addTimeIntervals(repeatedTimeInterval.ti, repeatedTimeInterval.n)
    }
}

operator fun MyDate.rangeTo(other: MyDate): DateRange = DateRange(this, other)



enum class TimeInterval {
    DAY,
    WEEK,
    YEAR;

    operator fun times(i: Int): RepeatedTimeInterval {
        return RepeatedTimeInterval(this, i)
    }
}

class DateRange(val start: MyDate, val endInclusive: MyDate) : Iterable<MyDate>{

    operator fun contains(date: MyDate): Boolean {
        return start <= date && date <= endInclusive
    }

    override fun iterator(): Iterator<MyDate> {
        return object : Iterator<MyDate> {

            private var current: MyDate? = null

            override fun hasNext(): Boolean {
                return wouldBeNext(current) <= endInclusive
            }

            override fun next(): MyDate {
                current = wouldBeNext(current);
                return current!!;
            }

            fun wouldBeNext(date : MyDate?): MyDate {
                return date?.nextDay() ?: start;
            }

        }
    }

}

class RepeatedTimeInterval(val ti: TimeInterval, val n: Int)

