/**
 * Copyright 2014 Rahul Parsani
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dengit.phrippple;

import android.graphics.Bitmap;
import android.support.v7.graphics.Palette;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dengit.phrippple.ui.SuperBaseActivity;
import com.dengit.phrippple.utils.Util;
import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.DataSource;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.request.ImageRequest;

import java.util.concurrent.Executors;

import butterknife.Bind;
import timber.log.Timber;

public abstract class AbstractDetailActivity extends SuperBaseActivity {

    @Bind(R.id.container)
    public View container;

    @Bind(R.id.photo)
    public ImageView hero;

    @Bind(R.id.target_image)
    protected SimpleDraweeView mTargetImage;

    public Bitmap photo;

    @Override
    public void onBackPressed() {
        setupExitAnimation();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //        if (photo != null && !photo.isRecycled()) {
        //            photo.recycle();
        //        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void initDetailActivity(final String url) {

        //so far as I know, Fresco can not handle gif when
        //encounter the following cases:
        //1, fetchImageFromBitmapCache, bitmap is null
        //2, setPostprocessor can not be called, SimpleDraweeView show nothing at the same time
        ImageRequest imageRequest = ImageRequest.fromUri(url);
        ImagePipeline imagePipeline = Fresco.getImagePipeline();
        DataSource<CloseableReference<CloseableImage>> dataSource =
                imagePipeline.fetchImageFromBitmapCache(imageRequest, null);
        dataSource.subscribe(new BaseBitmapDataSubscriber() {
            @Override
            protected void onNewResultImpl(Bitmap bitmap) {//gif bitmap is null

                RoundingParams roundingParams = mTargetImage.getHierarchy().getRoundingParams();
                boolean isRoundAsCircle = roundingParams != null && roundingParams.getRoundAsCircle();
                //todo animated view will have abnormal size when using background attr in xml
                if (bitmap == null) {
                    Timber.d("**setBackgroundResource, url: %s", url);
                    hero.setBackgroundResource(R.drawable.place_holder_shot);
                }

                photo = bitmap;
                if (isRoundAsCircle) {
                    hero.setImageBitmap(Util.getCircleBitmap(photo));
                } else {
                    hero.setImageBitmap(photo);
                }

                colorize(photo);

                postCreate();

                setupEnterAnimation();
            }

            @Override
            protected void onFailureImpl(
                    DataSource<CloseableReference<CloseableImage>> dataSource) {
                Timber.d("**onFailureImpl");
            }
        }, Executors.newSingleThreadExecutor());
    }

    public void applyPalette(Palette palette) {
        container.setBackgroundColor(palette.getDarkMutedColor(Util.getColor(R.color.default_dark_muted)));

        TextView titleView = (TextView) findViewById(R.id.title);
        titleView.setTextColor(palette.getVibrantColor(Util.getColor(R.color.default_vibrant)));

        TextView descriptionView = (TextView) findViewById(R.id.description);
        descriptionView.setTextColor(palette.getLightVibrantColor(Util.getColor(R.color.default_light_vibrant)));

        //        colorButton(R.id.info_button, palette.getDarkMutedColor(res.getColor(R.color.default_dark_muted)),
        //                palette.getDarkVibrantColor(res.getColor(R.color.default_dark_vibrant)));
        //        colorButton(R.id.star_button, palette.getMutedColor(res.getColor(R.color.default_muted)),
        //                palette.getVibrantColor(res.getColor(R.color.default_vibrant)));

    }

    public abstract void postCreate();

    public abstract void colorButton(int id, int bgColor, int tintColor);

    public abstract void setupEnterAnimation();

    public abstract void setupExitAnimation();

    private void setupText() {
        TextView titleView = (TextView) findViewById(R.id.title);
        titleView.setText(getIntent().getStringExtra("title"));

        TextView descriptionView = (TextView) findViewById(R.id.description);
        descriptionView.setText(getIntent().getStringExtra("description"));
    }

    private void colorize(Bitmap photo) {
        if (photo == null) {
            return;
        }

        Palette palette = Palette.from(photo).generate();
        //        applyPalette(palette);
    }
}
