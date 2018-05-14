﻿using System;
using System.Collections.Generic;
using System.Linq;

using Foundation;
using UIKit;
//using CarouselView.FormsPlugin.iOS;

namespace VocaKnow.iOS
{
    // The UIApplicationDelegate for the application. This class is responsible for launching the 
    // User Interface of the application, as well as listening (and optionally responding) to 
    // application events from iOS.
    [Register("AppDelegate")]
    public partial class AppDelegate : global::Xamarin.Forms.Platform.iOS.FormsApplicationDelegate
    {
        //
        // This method is invoked when the application has loaded and is ready to run. In this 
        // method you should instantiate the window, load the UI into it and then make the window
        // visible.
        //
        // You have 17 seconds to return from this method, or iOS will terminate your application.
        //
        public override bool FinishedLaunching(UIApplication app, NSDictionary options)
        {
            global::Xamarin.Forms.Forms.Init();

            //UIApplication.SharedApplication.SetStatusBarHidden(false, false);
            
            LoadApplication(new App());

            //SetThemeSkin();

            return base.FinishedLaunching(app, options);
        }

        private void SetThemeSkin()
        {
            UINavigationBar.Appearance.BarTintColor = UIColor.FromRGB(0, 123, 233);
            UINavigationBar.Appearance.TintColor = UIColor.White;
            UINavigationBar.Appearance.SetTitleTextAttributes(new UITextAttributes()
            {
                TextColor = UIColor.White
            });
        }

    }
}
