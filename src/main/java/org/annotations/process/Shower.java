/*
* Copyright (C) 2003-2009 eXo Platform SAS.
*
* This is free software; you can redistribute it and/or modify it
* under the terms of the GNU Lesser General Public License as
* published by the Free Software Foundation; either version 2.1 of
* the License, or (at your option) any later version.
*
* This software is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
* Lesser General Public License for more details.
*
* You should have received a copy of the GNU Lesser General Public
* License along with this software; if not, write to the Free
* Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
* 02110-1301 USA, or see the FSF site: http://www.fsf.org.
*/
package org.annotations.process;

import org.annotations.api.NullAnnotation;
import org.annotations.api.Showable;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:alain.defrance@exoplatform.com">Alain Defrance</a>
 * @version $Revision$
 */
public class Shower
{
   public void build(final Class<?> clazz)
   {
      if (clazz.isAnnotationPresent(Showable.class))
      {
         try
         {
            Field annotationsField = Class.class.getDeclaredField("annotations");
            annotationsField.setAccessible(true);
            Map<Class, Annotation> annotations =
                  new HashMap<Class, Annotation>((Map<? extends Class,? extends Annotation>) annotationsField.get(clazz));
            annotations.remove(Showable.class);
            annotations.put(Showable.class, new Showable() {

               public String show()
               {
                  StringBuilder stringBuilder = new StringBuilder();
                  stringBuilder.append(clazz.getName() + " :\n");
                  stringBuilder.append("+ Constructors :\n");
                  for (Constructor constructor : clazz.getConstructors())
                  {
                     stringBuilder.append(String.format("|-- %s ( ", constructor.getName()));
                     for (Class<?> parameterClass : constructor.getParameterTypes())
                     {
                        stringBuilder.append(String.format("%s ", parameterClass.getName()));
                     }
                     stringBuilder.append(")\n");
                  }
                  stringBuilder.append("+ Fields :\n");
                  for (Field field : clazz.getDeclaredFields())
                  {
                     stringBuilder.append(String.format("|-- %s %s\n", field.getType().getName(), field.getName()));
                  }
                  stringBuilder.append("+ Methods :\n");
                  for (Method method : clazz.getDeclaredMethods())
                  {
                     stringBuilder.append(String.format("|-- %s %s ( ", method.getReturnType().getName(), method.getName()));
                     for (Class<?> parameterClass : method.getParameterTypes())
                     {
                        stringBuilder.append(String.format("%s ", parameterClass.getName()));
                     }
                     stringBuilder.append(")\n");
                  }
                  return stringBuilder.toString();
               }

               public Class<? extends Annotation> annotationType()
               {
                  return Showable.class;
               }
            });
            annotationsField.set(clazz, annotations);
         }
         catch (NoSuchFieldException e){e.printStackTrace();}
         catch (IllegalAccessException e){e.printStackTrace();}
      }
   }
}
