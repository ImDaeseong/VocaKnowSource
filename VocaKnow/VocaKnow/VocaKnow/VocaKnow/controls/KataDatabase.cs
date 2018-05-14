using SQLite;
using System;
using System.Collections.Generic;
using System.Linq;
using VocaKnow.Interfaces;
using Xamarin.Forms;

namespace VocaKnow.controls
{
    public class KataDatabase
    {
        private static KataDatabase selfInstance = null;
        public static KataDatabase getInstance
        {
            get
            {
                if (selfInstance == null) selfInstance = new KataDatabase();
                return selfInstance;
            }
        }

        static object mlock = new object();
        SQLiteConnection sqLiteConnection;

        public KataDatabase()
        {
            sqLiteConnection = DependencyService.Get<ISqlite>().GetConnection();
            sqLiteConnection.CreateTable<KataItems>();
            sqLiteConnection.CreateTable<RegKataItems>();            
        }
        
        public IEnumerable<KataItems> GetItems()
        {
            lock (mlock)
            {
                return (from i in sqLiteConnection.Table<KataItems>() select i).ToList();
            }
        }

        public IEnumerable<KataItems> GetItemsPart(string sPart)
        {
            lock (mlock)
            {
                return sqLiteConnection.Query<KataItems>($"SELECT * FROM [Katas] WHERE Part1 = '{sPart}'").ToList();
            }
        }

        public IEnumerable<KataItems> GetItemsPart(string sPart1, string sPart2)
        {
            lock (mlock)
            {
                return sqLiteConnection.Query<KataItems>($"SELECT * FROM [Katas] WHERE Part1 = '{sPart1}' and Part2 = '{sPart2}'").ToList();
            }
        }

        public IEnumerable<KataItems> GetSearchKata(string sSearch)
        {
            lock (mlock)
            {
                return sqLiteConnection.Query<KataItems>(String.Format("SELECT * FROM [Katas] WHERE [KataIndo] LIKE \"{0}%\"", sSearch));
            }
        }

        public void UpdateKalimat(string sKataIndo, string sKataIndoTambah, string sSavedKataIndo)
        {
            try
            {
                string sValue1 = NullVal(sKataIndo, "").Replace("\"", "").Replace("\r", "").Replace("\n", "").Replace("\t", "").Trim();
                string sValue2 = NullVal(sKataIndoTambah, "").Replace("\"", "").Replace("\r", "").Replace("\n", "").Replace("\t", "").Trim();
                string sValue3 = NullVal(sSavedKataIndo, "").Replace("\"", "").Replace("\r", "").Replace("\n", "").Replace("\t", "").Trim();

                lock (mlock)
                {
                    sqLiteConnection.Query<KataItems>("UPDATE Katas set KataIndo=?, lembutlidah=? Where KataIndo=?", sValue1, sValue2, sValue3);
                }
            }catch{}
        }
        



        public IEnumerable<RegKataItems> GetRegItems()
        {
            lock (mlock)
            {
                return (from i in sqLiteConnection.Table<RegKataItems>() select i).ToList();
            }
        }

        public IEnumerable<RegKataItems> GetRegSearchKata(string sSearch)
        {
            lock (mlock)
            {
                var qry = sqLiteConnection.Query<RegKataItems>(String.Format("SELECT * FROM [RegKatas] WHERE [KataIndo] LIKE \"{0}%\"", sSearch));
                if (qry.Count > 0)
                {
                    return sqLiteConnection.Query<RegKataItems>(String.Format("SELECT * FROM [RegKatas] WHERE [KataIndo] LIKE \"{0}%\"", sSearch));
                }

                qry = sqLiteConnection.Query<RegKataItems>(String.Format("SELECT * FROM [RegKatas] WHERE [KataIndoTambah] LIKE \"{0}%\"", sSearch));
                if (qry.Count > 0)
                {
                    return sqLiteConnection.Query<RegKataItems>(String.Format("SELECT * FROM [RegKatas] WHERE [KataIndoTambah] LIKE \"{0}%\"", sSearch));
                }

                qry = sqLiteConnection.Query<RegKataItems>(String.Format("SELECT * FROM [RegKatas] WHERE [KataKor] LIKE \"{0}%\"", sSearch));
                if (qry.Count > 0)
                {
                    return sqLiteConnection.Query<RegKataItems>(String.Format("SELECT * FROM [RegKatas] WHERE [KataKor] LIKE \"{0}%\"", sSearch));
                }
                return null;
            }               
        }

        public bool IsExistKata(string sKataIndo)
        {
            var qry = sqLiteConnection.Query<RegKataItems>("SELECT * FROM RegKatas WHERE KataIndo=?", sKataIndo);
            if (qry.Count > 0)
            {
                return true;
            }
            else
            {
                return false;
            }
        }

        public void InsertRegKata(string sKataIndo, string sKataIndoTambah, string sKataKor)
        {
            try
            {
                string sValue1 = NullVal(sKataIndo, "").Replace("\"", "").Replace("\r", "").Replace("\n", "").Replace("\t", "").Trim();
                string sValue2 = NullVal(sKataIndoTambah, "").Replace("\"", "").Replace("\r", "").Replace("\n", "").Replace("\t", "").Trim();
                string sValue3 = NullVal(sKataKor, "").Replace("\"", "").Replace("\r", "").Replace("\n", "").Replace("\t", "").Trim();
               
                lock (mlock)
                {
                    RegKataItems item = new RegKataItems()
                    {
                        KataIndo = sValue1,
                        KataIndoTambah = sValue2,
                        KataKor = sValue3                        
                    };

                    if (sqLiteConnection.Insert(item).Equals(0))
                        sqLiteConnection.Update(item);
                }
            }
            catch
            {
            }
        }

        public void UpdateRegKata(string sSavedKataIndo, string sKataIndo, string sKataIndoTambah, string sKataKor)
        {
            try
            {
                string sValue1 = NullVal(sKataIndo, "").Replace("\"", "").Replace("\r", "").Replace("\n", "").Replace("\t", "").Trim();
                string sValue2 = NullVal(sKataIndoTambah, "").Replace("\"", "").Replace("\r", "").Replace("\n", "").Replace("\t", "").Trim();
                string sValue3 = NullVal(sKataKor, "").Replace("\"", "").Replace("\r", "").Replace("\n", "").Replace("\t", "").Trim();
                string sValue4 = NullVal(sSavedKataIndo, "").Replace("\"", "").Replace("\r", "").Replace("\n", "").Replace("\t", "").Trim();

                lock (mlock)
                {
                    sqLiteConnection.Query<RegKataItems>("UPDATE RegKatas set KataIndo=?, KataIndoTambah=?, KataKor=? Where KataIndo=?", sValue1, sValue2, sValue3, sValue4);
                }
            }
            catch
            {
            }
        }

        public void DeleteRegKata(string sKataIndo)
        {
            try
            {
                string sValue1 = NullVal(sKataIndo, "").Replace("\"", "").Replace("\r", "").Replace("\n", "").Replace("\t", "").Trim();
              
                lock (mlock)
                {
                    sqLiteConnection.Query<RegKataItems>("DELETE FROM RegKatas WHERE KataIndo=?", sValue1);
                }
            }
            catch
            {
            }
        }

        private string NullVal(object src, string Value)
        {
            if (src != null)
                return src.ToString();
            return Value;
        }

        private string QStr(string sValue)
        {
            return "'" + sValue.Replace("'", "''") + "'";
        }

    }
}
