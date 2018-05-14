using SQLite;

namespace VocaKnow.Interfaces
{
    [Table("RegKatas")]
    public class RegKataItems
    {
        public RegKataItems()
        {
        }

        [PrimaryKey, AutoIncrement]
        public int rIndex { get; set; }
        public string KataKor { get; set; }
        public string KataIndo { get; set; }
        public string KataIndoTambah { get; set; }
    }
}
