using System;

namespace VocaKnow.Interfaces
{
    public interface IMessageShow
    {
        void LongAlert(string message);
        void ShortAlert(string message);
    }
}
