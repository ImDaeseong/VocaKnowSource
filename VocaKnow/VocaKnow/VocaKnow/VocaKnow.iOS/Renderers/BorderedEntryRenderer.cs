using System;
using CoreGraphics;
using UIKit;
using Xamarin.Forms.Platform.iOS;
using System.ComponentModel;
using Xamarin.Forms;
using VocaKnow.Renderers;
using VocaKnow.iOS.Renderers;

[assembly: ExportRenderer(typeof(BorderedEntry), typeof(BorderedEntryRenderer))]
namespace VocaKnow.iOS.Renderers
{
    public class BorderedEntryRenderer : EntryRenderer
    {
        private BorderedLayer _border;
        private CGColor _borderColor;
        private CGColor _borderColorActive;
        private bool _hasBorder;
        private double _borderHeight;
        private bool _hasFocus;
        private int? _borderLayerIndex;

        protected override void OnElementChanged(ElementChangedEventArgs<Entry> e)
        {
            base.OnElementChanged(e);
            if (Control == null || Element == null) return;
            var element = (BorderedEntry)Element;
            UpdateProperties(element);
            Control.BackgroundColor = UIColor.Clear;
            Control.BorderStyle = UITextBorderStyle.None;
            Control.Started += Control_Started;
            Control.Ended += Control_Ended;
        }

        protected override void OnElementPropertyChanged(object sender, PropertyChangedEventArgs e)
        {
            base.OnElementPropertyChanged(sender, e);
            if (sender == null) return;
            var element = (BorderedEntry)sender;
            UpdateProperties(element);
            UpdateBorderColor();
        }

        public override void LayoutSubviews()
        {
            base.LayoutSubviews();
            if (Layer == null) return;
            if (!_hasBorder)
            {
                UpdateTransparentBorderColor();
            }
            _border = new BorderedLayer(_borderColor, _borderHeight, Frame.Size.Height, Frame.Size.Width);
            if (_borderLayerIndex == null)
            {
                Layer.AddSublayer(_border);
                _borderLayerIndex = Layer.Sublayers.Length - 1;
            }
            else
            {
                Layer.ReplaceSublayer(Layer.Sublayers[_borderLayerIndex.Value], _border);
            }
        }

        private void Control_Started(object sender, EventArgs e)
        {
            UpdateFocus(true);
            UpdateBorderColor();
        }

        private void Control_Ended(object sender, EventArgs e)
        {
            UpdateFocus(false);
            UpdateBorderColor();
        }

        private void UpdateFocus(bool hasFocus)
        {
            _hasFocus = hasFocus;
        }

        private void UpdateProperties(BorderedEntry borderedElement)
        {
            _borderColor = borderedElement.BorderColor.ToCGColor();
            _borderColorActive = borderedElement.BorderColorActive.ToCGColor();
            _hasBorder = borderedElement.HasBorder;
            _borderHeight = borderedElement.BorderHeight;
        }

        private void UpdateBorderColor()
        {
            if (!_hasBorder)
            {
                UpdateTransparentBorderColor();
            }
            if (_border == null) return;
            _border.BackgroundColor = _hasFocus ? _borderColorActive : _borderColor;
        }

        private void UpdateTransparentBorderColor()
        {
            _borderColor = Color.Transparent.ToCGColor();
            _borderColorActive = Color.Transparent.ToCGColor();
        }
    }
}