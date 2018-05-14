using SQLite;

namespace VocaKnow.Interfaces
{
    [Table("Katas")]
    public class KataItems
    {
        public KataItems()
        {
        }

        [PrimaryKey, AutoIncrement]
        public int rIndex { get; set; }
        public string Part1 { get; set; }
        public string Part2 { get; set; }
        public string KataKor { get; set; }
        public string KataIndo { get; set; }
        public string lembutlidah { get; set; }
    }
}
