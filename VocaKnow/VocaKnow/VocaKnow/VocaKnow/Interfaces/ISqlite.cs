using SQLite;

namespace VocaKnow.Interfaces
{
    public interface ISqlite
    {
        SQLiteConnection GetConnection();
    }
}
