package com.sprcore.android.mbf.ui;

import java.io.File;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sprcore.android.core.tools.wordpress.ContentFragment;
import com.sprcore.android.core.tools.wordpress.FileUtils;
import com.sprcore.android.core.tools.wordpress.ImageUtils;
import com.sprcore.android.core.tools.wordpress.MediaUtils;
import com.sprcore.android.mbf.base.AppActivity;
import com.sprcore.android.mbf.base.AppAsyncTask;
import com.sprcore.android.mbf.base.AppFragment;
import com.sprcore.android.mbf.base.AppSrModel;
import com.sprcore.android.mbf.helper.UploadFileHelper;
import com.sprcore.android.mbf.helper.model.UploadServiceAttachmentSpModel;

public class TestMainActivity extends AppActivity {

 
 

	@Override
	protected boolean isNeedLogin() {
		 
		return false;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState, Bundle intentExtras) {
		setContentView(R.layout.activity_search_service);
		addFragment(R.id.container, new PlaceholderFragment(), intentExtras);	
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends AppFragment {
		private TextView testmain1;
		private TextView testmain2;
		private TextView testmain3;
		
 
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_test_main,
					container, false);
			return rootView;
		}

		@Override
		protected void onCreate(Bundle savedInstanceState, Bundle intentExtras) {

		}

		@Override
		public void onActivityCreated(Bundle savedInstanceState) {
	 
			super.onActivityCreated(savedInstanceState);
			testmain1 = (TextView)findViewById(R.id.testview1);
			testmain1.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View arg0) {
					ContentFragment.launchCamera(getCurrentFragment(), new MediaUtils.LaunchCameraCallback() {
						@Override
						 public void onMediaCapturePathReady(String mediaCapturePath){
							getBaseActivity().showToast("launchCamera#mediaCapturePath:"+mediaCapturePath);
						}
					});
				}
			});
			
			testmain2 = (TextView)findViewById(R.id.testview2);
			testmain2.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View arg0) {	
					ContentFragment.launchPictureLibrary(getCurrentFragment());
				}
			});

			testmain3 = (TextView)findViewById(R.id.testview3);  
			testmain3.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View arg0) {	
					CharSequence[] items = {
							"相册选择",
							"拍照上传"
					};
					AlertDialog imageDialog = new AlertDialog.Builder(getBaseActivity()).setTitle("图片选择").setIcon(android.R.drawable.btn_star).setItems(items,
							new DialogInterface.OnClickListener(){
								public void onClick(DialogInterface dialog, int item){
									if( item == 0 ){ //从相册获取图片
						                Intent intent = new Intent(Intent.ACTION_PICK, null);
						                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
						                startActivityForResult(intent, ImageUtils.REQUEST_CODE_GETIMAGE_BYSDCARD);
						            }else if( item == 1 ){ //从拍照获取图片
						                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
						                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment
						                    .getExternalStorageDirectory(),"temp.jpg")));
						                startActivityForResult(intent, ImageUtils.REQUEST_CODE_GETIMAGE_BYCAMERA);
						            }	
								}}).create();
						
						 imageDialog.show();
				}
			});			
		}

		private String picFilePath;
		@Override
		public void onActivityResult(int requestCode, int resultCode,
				Intent data) {
			super.onActivityResult(requestCode, resultCode, data);
			if(resultCode != RESULT_OK) return;
			
			if(requestCode == ImageUtils.REQUEST_CODE_GETIMAGE_BYSDCARD) {
				//照片選擇
				String thePath = ImageUtils
						.getAbsolutePathFromNoStandardUri(data.getData());
				// 如果是标准Uri
				if (thePath == null || thePath.trim().length() == 0) {
					this.picFilePath = ImageUtils.getAbsoluteImagePath(
							getBaseActivity(), data.getData());
				} else {
					this.picFilePath = thePath;
				}
			}else{
				//拍攝照片
				this.picFilePath = Environment.getExternalStorageDirectory()
						+ "/temp.jpg";
			}
			new UploadPicTask().singleExecute(getCurrentFragment());
		}
		
		
		public class UploadPicTask extends AppAsyncTask<AppSrModel>{

			@Override
			protected AppSrModel doInBackground() {
				String imgName = FileUtils.getFileName(picFilePath);
				Bitmap bitmap = ImageUtils.loadImgThumbnail(getBaseActivity(), imgName, MediaStore.Images.Thumbnails.MICRO_KIND);
				try {
//					String tempFile = Environment.getExternalStorageDirectory().getAbsolutePath()+File.separator+"temp1.jpg";
//					ImageUtils.saveImage(getBaseActivity(),tempFile,bitmap,80);
//					picFilePath = tempFile;
					UploadServiceAttachmentSpModel spModel = new UploadServiceAttachmentSpModel();
					spModel.setBugId("0000004");
					spModel.setBugFileName(new File(picFilePath));
					return new UploadFileHelper().uploadServiceAttachment(spModel);	
				} catch (Exception e) {
					e.printStackTrace();
					AppSrModel srModel = new AppSrModel();
					srModel.setResultFlag("1");
					srModel.setResultMessage(e.getMessage());
					return srModel;
				}
	
			}

			@Override
			public boolean onPreExecute2() {
				if(picFilePath==null || picFilePath.trim().length()==0){
					return false;
				}else{
					return true;
				}
			}

			@Override
			protected void onPostExecute(AppSrModel srModel) {
				if(isSuccess(srModel)){
					getBaseActivity().showToast("xxx");
				}else{
					getBaseActivity().showToast("附件上传失败，失败原因:"+srModel.getResultMessage());
				}
			}			
		}	
		
		
	}
	
	

	
	
}
