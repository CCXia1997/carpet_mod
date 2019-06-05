package net.minecraft.util;

import com.google.common.collect.Lists;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterators.AbstractSpliterator;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class ReuseableStream<T>
{
    private final List<T> cachedValues = Lists.newArrayList();
    private final Iterator<T> iterator;

    public ReuseableStream(Stream<T> p_i49816_1_)
    {
        this.iterator = p_i49816_1_.iterator();
    }

    public Stream<T> createStream()
    {
        return StreamSupport.stream(new AbstractSpliterator<T>(Long.MAX_VALUE, 0)
        {
            private int nextIdx = 0;
            public boolean tryAdvance(Consumer <? super T > p_tryAdvance_1_)
            {
                T t;

                if (this.nextIdx >= ReuseableStream.this.cachedValues.size())
                {
                    if (!ReuseableStream.this.iterator.hasNext())
                    {
                        return false;
                    }

                    t = ReuseableStream.this.iterator.next();
                    ReuseableStream.this.cachedValues.add(t);
                }
                else
                {
                    t = ReuseableStream.this.cachedValues.get(this.nextIdx);
                }

                ++this.nextIdx;
                p_tryAdvance_1_.accept(t);
                return true;
            }
        }, false);
    }
}
