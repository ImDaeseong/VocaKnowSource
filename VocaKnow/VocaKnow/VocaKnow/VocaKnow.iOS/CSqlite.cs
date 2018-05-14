using System;
using System.IO;
using Foundation;
using Xamarin.Forms;
using SQLite;
using VocaKnow.Interfaces;
using VocaKnow.iOS;

[assembly: Dependency(typeof(CSqlite))]
namespace VocaKnow.iOS
{
    public class CSqlite : ISqlite
    {
        public CSqlite()
        {
        }

        public SQLiteConnection GetConnection()
        {
            var sqliteFilename = "IndonesiaDB.db";
            string documentsPath = Environment.GetFolderPath(Environment.SpecialFolder.Personal);
            string libraryPath = Path.Combine(documentsPath, "..", "Library");
            var path = Path.Combine(libraryPath, sqliteFilename);

            if (!File.Exists(path))
            {
                var existingDb = NSBundle.MainBundle.PathForResource("IndonesiaDB", "db");
                File.Copy(existingDb, path);
            }

            var conn = new SQLite.SQLiteConnection(path);
            return conn;
        }
    }
}