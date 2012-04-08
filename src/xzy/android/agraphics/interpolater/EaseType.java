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
/**
 * @author zhengyang.xu
 * @version 0.1
 * @date Mar 2, 2012 1:11:55 PM
 * @project AGraphics
 */
public interface EaseType {

    public static final float DOMAIN = 1.0f;

    public static final float DURATION = 1.0f;

    public static final float START = 0.0f;

    public enum Type {
        IN, OUT, INOUT
    }
}
