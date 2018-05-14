using System;
using Xamarin.Forms;
using System.Collections.Generic;

namespace VocaKnow.Renderers
{
    public class ImageCarousel : AbsoluteLayout
    {
        public static readonly BindableProperty ImagesProperty = BindableProperty.Create<ImageCarousel, IEnumerable<FileImageSource>>(p => p.Images, default(IEnumerable<FileImageSource>));

        public IEnumerable<FileImageSource> Images
        {
            get { return (IEnumerable<FileImageSource>)GetValue(ImagesProperty); }
            set { SetValue(ImagesProperty, value); }
        }

        List<Image> images;

        List<Image> ImageList
        {
            get
            {
                if (images == null)
                {
                    images = new List<Image>();
                }

                return images;
            }
        }

        public Image CurrentImage { get; private set; }

        public ImageCarousel()
        {
        }

        public ImageCarousel(IEnumerable<FileImageSource> images)
        {
            Images = images;
        }

        void addImagesAsChildren()
        {
            Children.Clear();

            var point = Point.Zero;

            foreach (var imageSource in Images)
            {
                var image = new Image
                {
                    Source = imageSource
                };

                this.Children.Add(image, new Rectangle(point, new Size(1, 1)), AbsoluteLayoutFlags.SizeProportional);
                point = new Point(point.X + image.Width, 0);
            }
        }

        protected override void LayoutChildren(double x, double y, double width, double height)
        {
            base.LayoutChildren(x, y, width, height);

            var point = Point.Zero;

            foreach (var image in ImageList)
            {
                image.Layout(new Rectangle(point, image.Bounds.Size));
                point = new Point(point.X + image.Width, 0);
            }
        }

        protected override void OnPropertyChanged(string propertyName = null)
        {
            base.OnPropertyChanged(propertyName);

            if (propertyName == ImagesProperty.PropertyName)
            {
                ImageList.Clear();
                CurrentImage = null;

                addImagesAsChildren();
            }
        }

        protected override void OnChildAdded(Element child)
        {
            base.OnChildAdded(child);

            if (child is Image)
            {
                ImageList.Add((Image)child);

                if (CurrentImage == null)
                {
                    CurrentImage = (Image)child;
                }
            }
        }

        public void OnSwipeLeft()
        {
            var imageNumber = ImageList.IndexOf(CurrentImage);
            var nextNumber = imageNumber == ImageList.Count - 1 ? 0 : imageNumber + 1;
            var nextImage = ImageList[nextNumber];

            nextImage.Layout(new Rectangle(new Point(CurrentImage.Width, 0), CurrentImage.Bounds.Size));

            var current = CurrentImage;

            current.LayoutTo(new Rectangle(-CurrentImage.Width, 0, CurrentImage.Width, CurrentImage.Height));
            CurrentImage = nextImage;
            nextImage.LayoutTo(new Rectangle(0, 0, CurrentImage.Width, CurrentImage.Height));
        }

        public void OnSwipeRight()
        {
            var imageNumber = ImageList.IndexOf(CurrentImage);
            var nextNumber = imageNumber == 0 ? ImageList.Count - 1 : imageNumber - 1;
            var nextImage = ImageList[nextNumber];

            nextImage.Layout(new Rectangle(new Point(-CurrentImage.Width, 0), CurrentImage.Bounds.Size));

            var current = CurrentImage;

            current.LayoutTo(new Rectangle(CurrentImage.Width, 0, CurrentImage.Width, CurrentImage.Height));
            CurrentImage = nextImage;
            nextImage.LayoutTo(new Rectangle(0, 0, CurrentImage.Width, CurrentImage.Height));
        }


        public int GetSelectedIndex()
        {
            var imageNumber = ImageList.IndexOf(CurrentImage);
            return imageNumber;
        }

    }
}
