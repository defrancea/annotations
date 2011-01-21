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
package org.annotations.api;

import junit.framework.TestCase;
import org.annotations.process.Nullifier;
import org.annotations.process.Shower;

/**
 * @author <a href="mailto:alain.defrance@exoplatform.com">Alain Defrance</a>
 * @version $Revision$
 */
public class AnnotationTestCase extends TestCase
{
   private Class<A> aClass = A.class;
   private Class<B> bClass = B.class;

   @Override
   protected void setUp() throws Exception
   {
      new Nullifier().setNull(aClass);
      new Shower().build(bClass);
   }

   public void testNull() throws Exception
   {
      assertEquals(null, aClass.getAnnotation(NullAnnotation.class).isNull());
   }

   public void testShow() throws Exception
   {
      assertEquals(
            "org.annotations.api.B :\n" +
            "+ Constructors :\n" +
            "|-- org.annotations.api.B ( java.lang.String )\n" +
            "|-- org.annotations.api.B ( java.lang.Integer )\n" +
            "|-- org.annotations.api.B ( java.lang.String java.lang.Integer )\n" +
            "+ Fields :\n" +
            "|-- java.lang.String foo\n" +
            "|-- java.lang.Integer bar\n" +
            "+ Methods :\n" +
            "|-- java.lang.Object toto ( java.lang.String )\n" +
            "|-- java.lang.String tata ( )\n",
            bClass.getAnnotation(Showable.class).show());
   }
}
