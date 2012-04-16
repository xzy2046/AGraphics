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
public class ExpoInterpolator implements AInterpolator {

    private Type mType;

    public ExpoInterpolator(Type type) {
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
        return (float) ((input == 0) ? 0 : Math.pow(2, 10 * (input - 1)));
    }

    private float out(float input) {
        return (float) ((input >= 1) ? 1 : (-Math.pow(2, -10 * input) + 1));
    }

    private float inout(float input) {
        if (input == 0) {
            return 0;
        }
        if (input >= 1) {
            return 1;
        }
        input *= 2;
        if (input < 1) {
            return (float) (0.5f * Math.pow(2, 10 * (input - 1)));
        } else {
            return (float) (0.5f * (-Math.pow(2, -10 * --input) + 2));
        }
    }
}
