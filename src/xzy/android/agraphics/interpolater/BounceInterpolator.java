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

package xzy.android.agraphics.interpolater;

import xzy.android.agraphics.interpolater.EaseType.Type;

/**
 * @author zhengyang.xu
 * @version 0.1
 * @date Mar 2, 2012 1:11:55 PM
 * @project AGraphics
 */
public class BounceInterpolator implements AInterpolator {

    private Type mType;

    public BounceInterpolator(Type type) {
        this.mType = type;
    }

    public void setType(Type type) {
        this.mType = type;
    }

    public float getInterpolation(float input) {

        if (mType == Type.IN) {
            return in(input);
        } else if (mType == Type.OUT) {
            return out(input);
        } else if (mType == Type.INOUT) {
            return inout(input);
        }
        return 0;
    }

    private static float bounce(float input) {
        return input * input * 8.0f;
    }

    private float out(float input) {
        // if (input < (1 / 2.75)) {
        // return 7.5625f * input * input;
        // } else if (input < 2 / 2.75) {
        // return 7.5625f * (input -= (1.5 / 2.75)) * input + .75f;
        // } else if (input < 2.5 / 2.75) {
        // return 7.5625f * (input -= (2.25 / 2.75)) * input + .9375f;
        // } else {
        // return 7.5625f * (input -= (2.625 / 2.75)) * input + .984375f;
        // }
        return 1 - in(1 - input);
    }

    private float in(float input) {
        // _b(t) = t * t * 8
        // bs(t) = _b(t) for t < 0.3535
        // bs(t) = _b(t - 0.54719) + 0.7 for t < 0.7408
        // bs(t) = _b(t - 0.8526) + 0.9 for t < 0.9644
        // bs(t) = _b(t - 1.0435) + 0.95 for t <= 1.0
        // b(t) = bs(t * 1.1226)
        input *= 1.1226f;
        if (input < 0.3535f)
            return bounce(input);
        else if (input < 0.7408f)
            return bounce(input - 0.54719f) + 0.7f;
        else if (input < 0.9644f)
            return bounce(input - 0.8526f) + 0.9f;
        else
            return bounce(input - 1.0435f) + 0.95f;

        // return 1 - out(1 - input);
    }

    private float inout(float input) {
        if (input < 0.5f) {
            return in(input * 2) * .5f;
        } else {
            return out(input * 2 - 1) * .5f + .5f;
        }
    }
}
