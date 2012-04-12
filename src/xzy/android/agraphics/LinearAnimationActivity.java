/*
 *  This program is a lightweight graphic library base on Android platform
 *  Copyright (C) 2012  Xu Zhengyang
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package xzy.android.agraphics;

import xzy.android.agraphics.interpolater.AnticipateInterpolator;
import xzy.android.agraphics.interpolater.BounceInterpolator;
import xzy.android.agraphics.interpolater.CircInterpolator;
import xzy.android.agraphics.interpolater.CubicInterpolator;
import xzy.android.agraphics.interpolater.EaseType.Type;
import xzy.android.agraphics.interpolater.ElasticInterpolator;
import xzy.android.agraphics.interpolater.ExpoInterpolator;
import xzy.android.agraphics.interpolater.QuadInterpolator;
import xzy.android.agraphics.interpolater.QuartInterpolator;
import xzy.android.agraphics.interpolater.QuintInterpolator;
import xzy.android.agraphics.interpolater.SineInterpolator;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
/**
 * @author zhengyang.xu
 * @version 0.1
 * @date Mar 2, 2012 1:11:55 PM
 * @project AGraphics
 */
public class LinearAnimationActivity extends Activity {

//    private AAnimator mAnimator;

    private Button mButton;

    private Button mBtnSelInterpolator;

    private AlertDialog mAlertDialog;

    private AAnimationView mAnimationView;

    private String[] mInterpolators = {
            "AnticipateInterpolator", "BounceInterpolator", "CircInterpolator", "CubicInterpolator",
            "ElasticInterpolator", "ExpoInterpolator", "QuadInterpolator", "QuartInterpolator",
            "QuintInterpolator", "SineInterpolator",
    };

    private int mSelectedItem = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.linear_animation);
        mButton = (Button) this.findViewById(R.id.button);
        mBtnSelInterpolator = (Button) this.findViewById(R.id.btn_select_interpolator);

        mAnimationView = (AAnimationView) this.findViewById(R.id.animationView);

//        mAnimator = new AAnimator();  //TODO Animator 可以放到View里面去
//        mAnimator.setAnimationUpdateListener(new AAnimator.AnimationUpdateListener() {
//
//            @Override
//            public void AnimationUpdate(AAnimator animator) {
//                mAnimationView.invalidate();
//            }
//        });
//        mAnimator.setDuration(2000);
//        mAnimator.setupAnimate(mAnimationView.getValuesHolderX(), 0, 400);
//        mAnimator.setInterpolator(new QuadInterpolator(Type.IN));

        mButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                switch (mSelectedItem) {
                    case 0:
//                        mAnimator.setInterpolator(new AnticipateInterpolator(Type.IN, 0));
                        mAnimationView.setInterpoltor(new AnticipateInterpolator(Type.IN, 0));
                        break;
                    case 1:
//                        mAnimator.setInterpolator(new BounceInterpolator(Type.IN));
                        mAnimationView.setInterpoltor(new BounceInterpolator(Type.IN));
                        break;
                    case 2:
//                        mAnimator.setInterpolator(new CircInterpolator(Type.IN));
                        mAnimationView.setInterpoltor(new CircInterpolator(Type.IN));
                        break;
                    case 3:
//                        mAnimator.setInterpolator(new CubicInterpolator(Type.IN));
                        mAnimationView.setInterpoltor(new CubicInterpolator(Type.IN));
                        break;
                    case 4:
//                        mAnimator.setInterpolator(new ElasticInterpolator(Type.IN, 0, 0));
                        mAnimationView.setInterpoltor(new ElasticInterpolator(Type.IN, 0, 0));
                        break;
                    case 5:
//                        mAnimator.setInterpolator(new ExpoInterpolator(Type.IN));
                        mAnimationView.setInterpoltor(new ExpoInterpolator(Type.IN));
                        break;
                    case 6:
//                        mAnimator.setInterpolator(new QuadInterpolator(Type.IN));
                        mAnimationView.setInterpoltor(new QuadInterpolator(Type.IN));
                        break;
                    case 7:
//                        mAnimator.setInterpolator(new QuartInterpolator(Type.IN));
                        mAnimationView.setInterpoltor(new QuartInterpolator(Type.IN));
                        break;
                    case 8:
//                        mAnimator.setInterpolator(new QuintInterpolator(Type.IN));
                        mAnimationView.setInterpoltor(new QuintInterpolator(Type.IN));
                        break;
                    case 9:
//                        mAnimator.setInterpolator(new SineInterpolator(Type.IN));
                        mAnimationView.setInterpoltor(new SineInterpolator(Type.IN));
                        break;
                    default:
                        break;
                }
//                mAnimator.start();
                mAnimationView.startAnimation();
            }
        });
        mBtnSelInterpolator.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showSelectDialog();
            }
        });
    }

    public void showSelectDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setSingleChoiceItems(mInterpolators, 0, new OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                mSelectedItem = which;
                dialog.dismiss();
            }

        });
        builder.create();
        builder.show();
    }
}