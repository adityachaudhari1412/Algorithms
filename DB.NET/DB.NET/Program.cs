using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace DB.NET
{
    static class Program
    {
        /// <summary>
        /// The main entry point for the application.
        /// </summary>
        [STAThread]
        static void Main()
        {
            DB db = DB.OpenDB(@"C:\scratch\EDUDBROOT", "SOMEDB");

            DBTable dbTbl = new DBTable();
            //dbTbl._sName = "TItem";
            //List<DBField> lstdbf = new List<DBField>();
            //lstdbf.Add(new DBField()
            //{
            //    _fAllowNull = false,
            //    _fIsIndexed = true,
            //    _sName = "UID",
            //    _eft = EFieldType.Guid
            //});
            //lstdbf.Add(new DBField()
            //{
            //    _fAllowNull = false,
            //    _fIsIndexed = true,
            //    _sName = "Name",
            //    _eft = EFieldType.Text
            //});
            //lstdbf.Add(new DBField()
            //{
            //    _fAllowNull = false,
            //    _fIsIndexed = true,
            //    _sName = "Price",
            //    _eft = EFieldType.Decimal
            //});
            
            //dbTbl._lstField = lstdbf;
            //db.AddTable(dbTbl);
            //Object[] arrObjData = new Object[3];

           
            //arrObjData[0] = Guid.NewGuid();
            //arrObjData[1] = "Pencil";
            //arrObjData[2] = 60;

            //db.AddRow("TItem", arrObjData);

            //arrObjData = new Object[3];

            //arrObjData[0] = Guid.NewGuid();
            //arrObjData[1] = "Pen";
            //arrObjData[2] = 75;

            //db.AddRow("TItem", arrObjData);

            //arrObjData = new Object[4];

            //arrObjData[0] = Guid.NewGuid();
            //arrObjData[1] = "Mary Joe";
            //arrObjData[2] = new DateTime(1990, 3, 6);
            //arrObjData[3] = "AB-";
            //db.AddRow("TPerson", arrObjData);

            //arrObjData = new Object[4];

            //arrObjData[0] = Guid.NewGuid();
            //arrObjData[1] = "James Bond";
            //arrObjData[2] = new DateTime(1960, 6, 12);
            //arrObjData[3] = "A+";

            //db.AddRow("TPerson", arrObjData);

            db.AddIndex("TPerson", 1);




            Application.EnableVisualStyles();
            Application.SetCompatibleTextRenderingDefault(false);
            Application.Run(new Form1());
        }
    }
}
