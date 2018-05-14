using Xamarin.Forms.Platform.Android;
using Xamarin.Forms;
using System.ComponentModel;
using VocaKnow.Renderers;
using VocaKnow.Droid.Renderers;
using Color = Android.Graphics.Color;
using View = Android.Views.View;

[assembly: ExportRenderer(typeof(BorderedEntry), typeof(BorderedEntryRenderer))]
namespace VocaKnow.Droid.Renderers
{
    public class BorderedEntryRenderer : EntryRenderer
    {
        private BorderedDrawable _border;
        private Android.Graphics.Color _borderColor;
        private Color _borderColorActive;
        private bool _hasBorder;
        private double _borderHeight;
        private bool _hasFocus;

        protected override void OnElementChanged(ElementChangedEventArgs<Entry> e)
        {
            base.OnElementChanged(e);
            if (Control == null || Element == null) return;
            var element = (BorderedEntry)Element;
            UpdateProperties(element);
            if (!_hasBorder)
            {
                UpdateTransparentBorderColor();
            }
            _border = new BorderedDrawable(_borderColor, _borderHeight);
            Control.Background = _border;
            Control.FocusChange += Control_FocusChange;
        }

        protected override void OnElementPropertyChanged(object sender, PropertyChangedEventArgs e)
        {
            base.OnElementPropertyChanged(sender, e);
            if (sender == null) return;
            var element = (BorderedEntry)sender;
            UpdateProperties(element);
            UpdateBorderColor();
        }

        private void Control_FocusChange(object sender, View.FocusChangeEventArgs e)
        {
            if (e == null) return;
            UpdateFocus(e.HasFocus);
            UpdateBorderColor();
        }

        private void UpdateFocus(bool hasFocus)
        {
            _hasFocus = hasFocus;
        }

        private void UpdateProperties(BorderedEntry borderedElement)
        {
            _borderColor = borderedElement.BorderColor.ToAndroid();
            _borderColorActive = borderedElement.BorderColorActive.ToAndroid();
            _hasBorder = borderedElement.HasBorder;
            _borderHeight = borderedElement.BorderHeight;
        }

        private void UpdateBorderColor()
        {
            if (!_hasBorder)
            {
                UpdateTransparentBorderColor();
            }
            _border.BackgroundPaint.Color = _hasFocus ? _borderColorActive : _borderColor;
        }

        private void UpdateTransparentBorderColor()
        {
            _borderColor = Color.Transparent;
            _borderColorActive = Color.Transparent;
        }
    }
}