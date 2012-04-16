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
// the same of AccelerateInterpolator
public class QuadInterpolator implements AInterpolator {

    private Type mType;

    public QuadInterpolator(Type type) {
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

    private float in(float input) {
        return input * input;
    }

    private float out(float input) {
        return -(input) * (input - 2);
    }

    private float inout(float input) {
        input *= 2;
        if (input < 1) {
            return 0.5f * input * input;
        } else {
            return -0.5f * ((--input) * (input - 2) - 1);
        }
    }
}
