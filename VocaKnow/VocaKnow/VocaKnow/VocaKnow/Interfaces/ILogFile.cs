using System;
using System.Threading.Tasks;

namespace VocaKnow.Interfaces
{
    public interface ILogFile
    {
        Task WriteString(string sText);
    }
}
