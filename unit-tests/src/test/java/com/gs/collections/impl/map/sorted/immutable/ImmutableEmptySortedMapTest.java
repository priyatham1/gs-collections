/*
 * Copyright 2011 Goldman Sachs.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gs.collections.impl.map.sorted.immutable;

import java.util.Comparator;
import java.util.NoSuchElementException;

import com.gs.collections.api.block.function.Function2;
import com.gs.collections.api.map.ImmutableMap;
import com.gs.collections.api.map.sorted.ImmutableSortedMap;
import com.gs.collections.api.tuple.Pair;
import com.gs.collections.impl.block.factory.Comparators;
import com.gs.collections.impl.block.factory.Functions;
import com.gs.collections.impl.block.factory.Predicates;
import com.gs.collections.impl.block.factory.Predicates2;
import com.gs.collections.impl.block.function.PassThruFunction0;
import com.gs.collections.impl.factory.Lists;
import com.gs.collections.impl.factory.SortedMaps;
import com.gs.collections.impl.map.sorted.ImmutableSortedMapTestCase;
import com.gs.collections.impl.test.SerializeTestHelper;
import com.gs.collections.impl.test.Verify;
import com.gs.collections.impl.tuple.Tuples;
import org.junit.Assert;
import org.junit.Test;

/**
 * JUnit test for {@link ImmutableEmptySortedMap}.
 */
public class ImmutableEmptySortedMapTest extends ImmutableSortedMapTestCase
{
    @Override
    protected ImmutableSortedMap<Integer, String> classUnderTest()
    {
        return SortedMaps.immutable.of();
    }

    @Override
    protected ImmutableSortedMap<Integer, String> classUnderTest(Comparator<? super Integer> comparator)
    {
        return SortedMaps.immutable.of(comparator);
    }

    @Override
    protected int size()
    {
        return 0;
    }

    @Override
    @Test
    public void testToString()
    {
        ImmutableSortedMap<Integer, String> map = this.classUnderTest();
        Assert.assertEquals("{}", map.toString());
    }

    @Override
    @Test
    public void get()
    {
        Integer absentKey = this.size() + 1;
        String absentValue = String.valueOf(absentKey);

        // Absent key behavior
        ImmutableSortedMap<Integer, String> classUnderTest = this.classUnderTest();
        Assert.assertNull(classUnderTest.get(absentKey));
        Assert.assertFalse(classUnderTest.containsValue(absentValue));

        // Still unchanged
        Assert.assertEquals(this.equalUnifiedMap(), classUnderTest);
    }

    @Override
    @Test
    public void getIfAbsent()
    {
        Integer absentKey = this.size() + 1;
        String absentValue = String.valueOf(absentKey);

        // Absent key behavior
        ImmutableSortedMap<Integer, String> classUnderTest = this.classUnderTest();
        Assert.assertEquals(absentValue, classUnderTest.getIfAbsent(absentKey, new PassThruFunction0<String>(absentValue)));

        // Still unchanged
        Assert.assertEquals(this.equalUnifiedMap(), classUnderTest);
    }

    @Override
    @Test
    public void getIfAbsentWith()
    {
        Integer absentKey = this.size() + 1;
        String absentValue = String.valueOf(absentKey);

        // Absent key behavior
        ImmutableSortedMap<Integer, String> classUnderTest = this.classUnderTest();
        Assert.assertEquals(absentValue, classUnderTest.getIfAbsentWith(absentKey, Functions.getToString(), absentValue));

        // Still unchanged
        Assert.assertEquals(this.equalUnifiedMap(), classUnderTest);
    }

    @Override
    @Test
    public void ifPresentApply()
    {
        Integer absentKey = this.size() + 1;

        ImmutableSortedMap<Integer, String> classUnderTest = this.classUnderTest();
        Assert.assertNull(classUnderTest.ifPresentApply(absentKey, Functions.<String>getPassThru()));
    }

    @Override
    @Test
    public void notEmpty()
    {
        Assert.assertFalse(this.classUnderTest().notEmpty());
    }

    @Test
    public void allSatisfy()
    {
        ImmutableSortedMap<String, String> map = new ImmutableEmptySortedMap<String, String>();

        Assert.assertTrue(map.allSatisfy(Predicates.instanceOf(String.class)));
        Assert.assertTrue(map.allSatisfy(Predicates.equal("Monkey")));
    }

    @Test
    public void anySatisfy()
    {
        ImmutableSortedMap<String, String> map = new ImmutableEmptySortedMap<String, String>();

        Assert.assertFalse(map.anySatisfy(Predicates.instanceOf(String.class)));
        Assert.assertFalse(map.anySatisfy(Predicates.equal("Monkey")));
    }

    @Test(expected = NoSuchElementException.class)
    public void max()
    {
        this.classUnderTest().max();
    }

    @Test(expected = NoSuchElementException.class)
    public void maxBy()
    {
        this.classUnderTest().maxBy(Functions.getStringPassThru());
    }

    @Test(expected = NoSuchElementException.class)
    public void min()
    {
        this.classUnderTest().min();
    }

    @Test(expected = NoSuchElementException.class)
    public void minBy()
    {
        this.classUnderTest().minBy(Functions.getStringPassThru());
    }

    @Override
    @Test
    public void select()
    {
        ImmutableSortedMap<Integer, String> map = this.classUnderTest();
        ImmutableSortedMap<Integer, String> actual = map.select(Predicates2.alwaysTrue());
        Verify.assertInstanceOf(ImmutableEmptySortedMap.class, actual);
        Assert.assertSame(ImmutableEmptySortedMap.INSTANCE, actual);

        ImmutableSortedMap<Integer, String> revMap = this.classUnderTest(Comparators.<Integer>reverseNaturalOrder());
        ImmutableSortedMap<Integer, String> revActual = revMap.select(Predicates2.alwaysTrue());
        Verify.assertInstanceOf(ImmutableEmptySortedMap.class, revActual);
        Assert.assertSame(revMap.comparator(), revActual.comparator());
    }

    @Override
    @Test
    public void reject()
    {
        ImmutableSortedMap<Integer, String> map = this.classUnderTest();
        ImmutableSortedMap<Integer, String> actual = map.reject(Predicates2.alwaysFalse());
        Verify.assertInstanceOf(ImmutableEmptySortedMap.class, actual);
        Assert.assertSame(ImmutableEmptySortedMap.INSTANCE, actual);

        ImmutableSortedMap<Integer, String> revMap = this.classUnderTest(Comparators.<Integer>reverseNaturalOrder());
        ImmutableSortedMap<Integer, String> revActual = revMap.reject(Predicates2.alwaysTrue());
        Verify.assertInstanceOf(ImmutableEmptySortedMap.class, revActual);
        Assert.assertSame(revMap.comparator(), revActual.comparator());
    }

    @Override
    @Test
    public void collect()
    {
        ImmutableSortedMap<Integer, String> map = this.classUnderTest();
        ImmutableSortedMap<Integer, String> revMap = this.classUnderTest(Comparators.<Integer>reverseNaturalOrder());

        Function2<Integer, String, Pair<Integer, String>> alwaysTrueFunction = new Function2<Integer, String, Pair<Integer, String>>()
        {
            public Pair<Integer, String> value(Integer argument1, String argument2)
            {
                return Tuples.pair(argument1, argument2);
            }
        };
        ImmutableMap<Integer, String> collect = map.collect(alwaysTrueFunction);
        ImmutableMap<Integer, String> revCollect = revMap.collect(alwaysTrueFunction);

        Verify.assertEmpty(collect);
        Assert.assertSame(collect, revCollect);
    }

    @Test
    public void detect()
    {
        ImmutableSortedMap<Integer, String> map = this.classUnderTest();
        Assert.assertNull(map.detect(Predicates2.alwaysTrue()));
    }

    @Test
    public void containsKey()
    {
        ImmutableSortedMap<Integer, String> map = this.classUnderTest();
        ImmutableSortedMap<Integer, String> revMap = this.classUnderTest(Comparators.<Integer>reverseNaturalOrder());
        Assert.assertFalse(map.containsKey(0));
        Assert.assertFalse(revMap.containsKey(1));
    }

    @Test
    public void values()
    {
        ImmutableEmptySortedMap<Integer, String> map = (ImmutableEmptySortedMap<Integer, String>)
                this.classUnderTest();
        ImmutableEmptySortedMap<Integer, String> revMap = (ImmutableEmptySortedMap<Integer, String>)
                this.classUnderTest(Comparators.<Integer>reverseNaturalOrder());

        Verify.assertEmpty(map.values());
        Assert.assertSame(Lists.immutable.of(), map.values());

        Verify.assertEmpty(revMap.values());

        Assert.assertSame(Lists.immutable.of(), revMap.values());
    }

    @Test
    public void serialization()
    {
        ImmutableSortedMap<Integer, String> map = this.classUnderTest();
        ImmutableSortedMap<Integer, String> deserialized = SerializeTestHelper.serializeDeserialize(map);
        Assert.assertSame(ImmutableEmptySortedMap.INSTANCE, map);
        Assert.assertSame(map, deserialized);

        ImmutableSortedMap<Integer, String> revMap = this.classUnderTest(Comparators.<Integer>reverseNaturalOrder());
        ImmutableSortedMap<Integer, String> revDeserialized = SerializeTestHelper.serializeDeserialize(revMap);
        Verify.assertInstanceOf(ImmutableSortedMap.class, revDeserialized);
        Assert.assertNotNull(revDeserialized.comparator());
    }

    @Test
    public void keyValuesView()
    {
        Assert.assertTrue(this.classUnderTest().keyValuesView().isEmpty());
    }
}
