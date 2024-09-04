import org.junit.Assert.assertEquals
import org.junit.Test
import com.glucode.about_you.engineers.models.Engineer
import com.glucode.about_you.engineers.models.QuickStats

class EngineersSortingTest {

    private val engineers = listOf(
        Engineer(name = "Reenen", role = "Dev manager", defaultImageName = "", quickStats = QuickStats(years = 6, coffees = 5400, bugs = 1800), questions = emptyList()),
        Engineer(name = "Wilmar", role = "Head of Engineering", defaultImageName = "", quickStats = QuickStats(years = 15, coffees = 4000, bugs = 4000), questions = emptyList()),
        Engineer(name = "Eben", role = "Head of Testing", defaultImageName = "", quickStats = QuickStats(years = 14, coffees = 1000, bugs = 100), questions = emptyList()),
        Engineer(name = "Stefan", role = "Senior dev", defaultImageName = "", quickStats = QuickStats(years = 7, coffees = 9000, bugs = 700), questions = emptyList()),
        Engineer(name = "Brandon", role = "Senior dev", defaultImageName = "", quickStats = QuickStats(years = 9, coffees = 99999, bugs = 99999), questions = emptyList()),
        Engineer(name = "Henri", role = "Senior dev", defaultImageName = "", quickStats = QuickStats(years = 10, coffees = 1800, bugs = 1000), questions = emptyList())
    )

    @Test
    fun testSortEngineersByYears() {
        val sortedEngineers = engineers.sortedBy { it.quickStats.years }
        assertEquals(
            listOf("Reenen", "Stefan", "Brandon", "Henri", "Eben", "Wilmar"),
            sortedEngineers.map { it.name }
        )
    }

    @Test
    fun testSortEngineersByCoffees() {
        val sortedEngineers = engineers.sortedBy { it.quickStats.coffees }
        assertEquals(
            listOf("Eben", "Henri", "Wilmar", "Reenen", "Stefan", "Brandon"),
            sortedEngineers.map { it.name }
        )
    }

    @Test
    fun testSortEngineersByBugs() {
        val sortedEngineers = engineers.sortedBy { it.quickStats.bugs }
        assertEquals(
            listOf("Eben", "Stefan", "Henri", "Reenen", "Wilmar", "Brandon"),
            sortedEngineers.map { it.name }
        )
    }
}
