package mono.com.google.android.gms.common.moduleinstall;


public class InstallStatusListenerImplementor
	extends java.lang.Object
	implements
		mono.android.IGCUserPeer,
		com.google.android.gms.common.moduleinstall.InstallStatusListener
{
/** @hide */
	public static final String __md_methods;
	static {
		__md_methods = 
			"n_onInstallStatusUpdated:(Lcom/google/android/gms/common/moduleinstall/ModuleInstallStatusUpdate;)V:GetOnInstallStatusUpdated_Lcom_google_android_gms_common_moduleinstall_ModuleInstallStatusUpdate_Handler:Android.Gms.Common.ModuleInstall.IInstallStatusListenerInvoker, Xamarin.GooglePlayServices.Base\n" +
			"";
		mono.android.Runtime.register ("Android.Gms.Common.ModuleInstall.IInstallStatusListenerImplementor, Xamarin.GooglePlayServices.Base", InstallStatusListenerImplementor.class, __md_methods);
	}


	public InstallStatusListenerImplementor ()
	{
		super ();
		if (getClass () == InstallStatusListenerImplementor.class) {
			mono.android.TypeManager.Activate ("Android.Gms.Common.ModuleInstall.IInstallStatusListenerImplementor, Xamarin.GooglePlayServices.Base", "", this, new java.lang.Object[] {  });
		}
	}


	public void onInstallStatusUpdated (com.google.android.gms.common.moduleinstall.ModuleInstallStatusUpdate p0)
	{
		n_onInstallStatusUpdated (p0);
	}

	private native void n_onInstallStatusUpdated (com.google.android.gms.common.moduleinstall.ModuleInstallStatusUpdate p0);

	private java.util.ArrayList refList;
	public void monodroidAddReference (java.lang.Object obj)
	{
		if (refList == null)
			refList = new java.util.ArrayList ();
		refList.add (obj);
	}

	public void monodroidClearReferences ()
	{
		if (refList != null)
			refList.clear ();
	}
}
